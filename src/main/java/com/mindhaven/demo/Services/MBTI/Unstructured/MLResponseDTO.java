package com.mindhaven.demo.Services.MBTI.Unstructured;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MLResponseDTO{
    Map<String, Map<String, Double>> probabilities;
}
