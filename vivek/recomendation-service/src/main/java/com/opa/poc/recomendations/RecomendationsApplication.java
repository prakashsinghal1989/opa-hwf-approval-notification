package com.opa.poc.recomendations;

import com.opa.poc.recomendations.util.RecommendationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RecomendationsApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RecommendationUtils recommendationUtils(){
        return new RecommendationUtils();
    }

    public static void main(String[] args) {SpringApplication.run(RecomendationsApplication.class, args);}

}
