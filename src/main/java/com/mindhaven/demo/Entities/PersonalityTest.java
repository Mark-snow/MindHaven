package com.mindhaven.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PersonalityTest {

    @Id
    private String questionId;
    private String text;
    
    @Enumerated(EnumType.STRING)
    private MBTIAxis axis; // E_I, S_N, T_F, J_P  
    private boolean isReverseScored;
    
    // Default constructor for JPA
    public PersonalityTest() {
    }

    public PersonalityTest(String questionId, String text, MBTIAxis axis, boolean isReverseScored) {
        this.questionId = questionId;
        this.text = text;
        this.axis = axis;
        this.isReverseScored = isReverseScored;
    }
}
