package com.mindhaven.demo.Services.MBTI.Structured;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StructuredResponseDTO {

    @NotBlank
    private String questionId;
    
    @Min(1) @Max(5)
    private int score;
}
