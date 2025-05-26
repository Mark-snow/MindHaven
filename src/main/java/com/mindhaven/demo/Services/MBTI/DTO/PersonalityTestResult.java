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


    public PersonalityTestResult(Long userId, String predictedType, Map<MBTIAxis, Double> axisProbabilities) {
        this.axisProbabilities = axisProbabilities;
        this.userId = userId;
        this.predictedType = predictedType;
    }
}