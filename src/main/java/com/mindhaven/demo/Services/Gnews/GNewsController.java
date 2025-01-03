package com.mindhaven.demo.Services.Gnews;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/GNews-mental-health")
@CrossOrigin("*")
public class GNewsController {

    private final GNewsApiService gNewsApiService;

    public GNewsController(GNewsApiService gNewsApiService) {
        this.gNewsApiService = gNewsApiService;
    }

    @GetMapping("/articles")
    public String getMentalHealthArticles() {
        return gNewsApiService.getMentalHealthArticles();
    }

    @GetMapping("/articles/filter")
    public String getMentalHealthArticles(@RequestParam String keywords) {
        return gNewsApiService.getCustomMentalHealthArticles(keywords);
    }

    @GetMapping("/news")
    public String getMentalHealthNews() {
        return gNewsApiService.getMentalHealthNews();
    }
}