package com.mindhaven.demo.Services.Articles;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponseDto {
    private LocalDate publicationDate;
    private LocalDate lastModified;
    private String urlName;
    private String summary;
    private String source;
    private String content;
    private String title;
    private String subtitle;

}
