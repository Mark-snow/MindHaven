package com.mindhaven.demo.Services.MBTI.Aggregation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.MBTIAxis;
import com.mindhaven.demo.Entities.PersonalityTest;
import com.mindhaven.demo.Exceptions.MLServiceException;
import com.mindhaven.demo.Repositories.PersonalityTestRepository;
import com.mindhaven.demo.Services.MBTI.DTO.PersonalityTestResult;
import com.mindhaven.demo.Services.MBTI.Structured.MbtiCalculatorService;
import com.mindhaven.demo.Services.MBTI.Structured.StructuredResponseDTO;
import com.mindhaven.demo.Services.MBTI.Unstructured.MLClientService;

@Service
public class HybridMbtiService {

    private static final Logger logger = LoggerFactory.getLogger(HybridMbtiService.class);
    
    @Autowired
    private MbtiCalculatorService structuredService;

    @Autowired
    private MLClientService mlClient;

    @Autowired
    private PersonalityTestRepository questionRepository;

    public PersonalityTestResult getHybridType(
        Long userId,
        List<StructuredResponseDTO> structuredResponses,
        String unstructuredText) {
        
        // 1. Always calculate structured probabilities first
        Map<MBTIAxis, Double> structuredProbs = structuredService
            .calculateTraitProbabilities(structuredResponses, loadQuestions());
        
        // 2. Try to get ML probabilities with fallback
        Map<MBTIAxis, Double> finalProbs;
        String resultSource;
        
        try {
            Map<MBTIAxis, Double> mlProbs = mlClient.predictProbabilities(unstructuredText);
            finalProbs = combineProbabilities(structuredProbs, mlProbs);
            resultSource = "HYBRID";
            logger.info("Successfully used hybrid scoring for user {}", userId);
        } catch (MLServiceException e) {
            finalProbs = structuredProbs;
            resultSource = "STRUCTURED_ONLY";
            logger.warn("ML service unavailable, falling back to structured results for user {}", userId);
        }

        // 3. Determine final type
        String mbtiType = determineType(finalProbs);
        
        return new PersonalityTestResult(userId, mbtiType, finalProbs, resultSource);
    }

    private Map<MBTIAxis, Double> combineProbabilities(
        Map<MBTIAxis, Double> structured, 
        Map<MBTIAxis, Double> ml) {
        
        Map<MBTIAxis, Double> combined = new EnumMap<>(MBTIAxis.class);
        Arrays.stream(MBTIAxis.values()).forEach(axis -> {
            combined.put(axis, 
                (structured.get(axis) * 0.8) + // 80% weight to structured
                (ml.get(axis) * 0.2)          // 20% to ML
            );
        });
        return combined;
    }

    @Cacheable("mbtiQuestions")
    public Map<String, PersonalityTest> loadQuestions() {
        return questionRepository.findAll().stream()
            .collect(Collectors.toMap(
                PersonalityTest::getQuestionId,
                Function.identity()
            ));
    }

    private String determineType(Map<MBTIAxis, Double> probs) {
        return String.format("%s%s%s%s",
            probs.get(MBTIAxis.E_I) > 0.5 ? "I" : "E",
            probs.get(MBTIAxis.S_N) > 0.5 ? "N" : "S",
            probs.get(MBTIAxis.T_F) > 0.5 ? "F" : "T",
            probs.get(MBTIAxis.J_P) > 0.5 ? "P" : "J"
        );
    }
}