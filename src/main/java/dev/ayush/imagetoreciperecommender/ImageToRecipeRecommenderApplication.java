package dev.ayush.imagetoreciperecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ImageToRecipeRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageToRecipeRecommenderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
