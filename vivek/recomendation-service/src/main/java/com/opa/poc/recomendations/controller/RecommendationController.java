package com.opa.poc.recomendations.controller;

import com.opa.poc.recomendations.model.TimePayload;
import com.opa.poc.recomendations.util.RecommendationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationUtils recommendationUtils;

    @PostMapping("/time")
    @CrossOrigin(origins = "http://localhost:3000")
    public String predictTime(@RequestBody TimePayload payload) {
        System.out.println("Inside predictTime of RecommendationController payload::"+payload.toString());
        return recommendationUtils.predictTime(payload);
    }
}
