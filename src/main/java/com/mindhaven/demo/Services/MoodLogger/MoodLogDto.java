package com.mindhaven.demo.Services.MoodLogger;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoodLogDto {

    @NotBlank(message = "Mood cannot be blank")
    private String mood;

    @NotBlank(message = "Description cannot be blank")
    private String description;

}
