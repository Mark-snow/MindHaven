package com.mindhaven.demo.Services.MBTI;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Entities.PersonalityTest;
import com.mindhaven.demo.Services.MBTI.Aggregation.HybridMbtiService;
import com.mindhaven.demo.Services.MBTI.DTO.PersonalityTestRequest;
import com.mindhaven.demo.Services.MBTI.DTO.PersonalityTestResult;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/personality-test")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PersonalityTestController {

    private final HybridMbtiService hybridService;

    @PostMapping("/submit")
    public ResponseEntity<PersonalityTestResult> calculateHybrid(
        @RequestBody PersonalityTestRequest request) {
        
        PersonalityTestResult result = hybridService.getHybridType(
            request.getUserId(),
            request.getStructuredResponses(),
            request.getUnstructuredText()
        );
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/questions")
    public ResponseEntity<Map<String, PersonalityTest>> getQuestions() {
        Map<String, PersonalityTest> questions = hybridService.loadQuestions();
        return ResponseEntity.ok(questions);
    }
}
