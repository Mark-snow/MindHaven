package com.mindhaven.demo.Services.Therapists;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TherapistDto {
    private String icon;
    private String name;
    private List<String> photos;
    private Double rating;
    private String vicinity;

}
