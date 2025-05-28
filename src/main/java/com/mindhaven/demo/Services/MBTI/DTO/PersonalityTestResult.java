package com.mindhaven.demo.Services.MBTI.DTO;

import java.util.Map;

import com.mindhaven.demo.Entities.MBTIAxis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalityTestResult {
    private Long userId;
    private String predictedType;
    Map<MBTIAxis, Double> axisProbabilities;
    private String resultSource; //to indicate if the result is from structured, unstructured, or hybrid


    public PersonalityTestResult(Long userId, String predictedType, Map<MBTIAxis, Double> axisProbabilities, String resultSource) {
        this.resultSource = resultSource;
        this.axisProbabilities = axisProbabilities;
        this.userId = userId;
        this.predictedType = predictedType;
    }
}