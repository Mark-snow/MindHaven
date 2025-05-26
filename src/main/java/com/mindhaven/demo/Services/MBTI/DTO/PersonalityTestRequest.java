package com.mindhaven.demo.Services.MBTI.DTO;

import java.util.List;

import com.mindhaven.demo.Services.MBTI.Structured.StructuredResponseDTO;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalityTestRequest {
    private Long userId;
    @Valid 
    private List<StructuredResponseDTO> structuredResponses; // List of structured responses
    private String unstructuredText; // free-text input
}
