package com.mindhaven.demo.Services.MoodLogger;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoodLogDto {

    private String mood;
    private String description;
    private String tag;

}
