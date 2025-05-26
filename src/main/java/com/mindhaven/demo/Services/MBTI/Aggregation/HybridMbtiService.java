package com.mindhaven.demo.Services.MBTI.Aggregation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.MBTIAxis;
import com.mindhaven.demo.Entities.PersonalityTest;
import com.mindhaven.demo.Repositories.PersonalityTestRepository;
import com.mindhaven.demo.Services.MBTI.DTO.PersonalityTestResult;
import com.mindhaven.demo.Services.MBTI.Structured.MbtiCalculatorService;
import com.mindhaven.demo.Services.MBTI.Structured.StructuredResponseDTO;
import com.mindhaven.demo.Services.MBTI.Unstructured.MLClientService;

@Service
public class HybridMbtiService {

    @Autowired
    private MbtiCalculatorService structuredService;

    @Autowired
    private MLClientService mlClient; // Python ML API client

    @Autowired
    private PersonalityTestRepository questionRepository; // Repository to fetch questions

    // Cache questions to avoid DB hits on every request
    @Cacheable("mbtiQuestions")
    public Map<String, PersonalityTest> loadQuestions() {
        return questionRepository.findAll().stream()
            .collect(Collectors.toMap(
                PersonalityTest::getQuestionId,
                Function.identity()
            ));
    }

    public PersonalityTestResult getHybridType(
        Long userId,
        List<StructuredResponseDTO> structuredResponses,
        String unstructuredText) {
        
        // 1. Get structured probabilities
        Map<MBTIAxis, Double> structuredProbs = structuredService
            .calculateTraitProbabilities(structuredResponses, loadQuestions());
        
        // 2. Get ML probabilities (from your Python API)
        Map<MBTIAxis, Double> mlProbs = mlClient.predictProbabilities(unstructuredText);
        
        // 3. Aggregate (weighted average example)
        Map<MBTIAxis, Double> combined = new EnumMap<>(MBTIAxis.class);
        Arrays.stream(MBTIAxis.values()).forEach(axis -> {
            combined.put(axis, 
                (structuredProbs.get(axis) * 0.8) + // 80% weight to structured
                (mlProbs.get(axis) * 0.2)          // 20% to ML
            );
        });

        // 4. Determine final type
        String mbtiType = determineType(combined);
        
        return new PersonalityTestResult(userId, mbtiType, combined);
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
