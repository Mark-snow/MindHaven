package com.mindhaven.demo.Services.MBTI.Structured;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.MBTIAxis;
import com.mindhaven.demo.Entities.PersonalityTest;

@Service
public class MbtiCalculatorService {
    
    // Normalized score ranges per axis
    private static final Map<MBTIAxis, Double> MAX_SCORES = Map.of(
        MBTIAxis.E_I, 15.0, // 3 questions * 5 points
        MBTIAxis.S_N, 15.0,
        MBTIAxis.T_F, 15.0,
        MBTIAxis.J_P, 15.0
    );

    public Map<MBTIAxis, Double> calculateTraitProbabilities(
        List<StructuredResponseDTO> responses,
        Map<String, PersonalityTest> questionMap) {
        
        Map<MBTIAxis, Double> axisScores = new EnumMap<>(MBTIAxis.class);
        
        // Initialize all axes
        Arrays.stream(MBTIAxis.values())
              .forEach(axis -> axisScores.put(axis, 0.0));

        // Sum scores (with reverse scoring where needed)
        for (StructuredResponseDTO response : responses) {
            PersonalityTest question = questionMap.get(response.getQuestionId());
            int score = question.isReverseScored() 
                ? (6 - response.getScore())  // Reverse 1-5 to 5-1
                : response.getScore();
            
            axisScores.merge(question.getAxis(), (double) score, Double::sum);
        }

        // Convert to probabilities (0.0 to 1.0)
        Map<MBTIAxis, Double> probabilities = new EnumMap<>(MBTIAxis.class);
        axisScores.forEach((axis, score) -> {
            double normalized = score / MAX_SCORES.get(axis);
            probabilities.put(axis, normalized);
        });

        return probabilities;
    }
}
