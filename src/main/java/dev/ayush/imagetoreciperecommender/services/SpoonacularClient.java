package dev.ayush.imagetoreciperecommender.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.net.URI;

@Component
public class SpoonacularClient {
    @Value("${spoonacular_key}")
    private String apiKey;

    private final RestTemplate restTemplate;


    @Autowired
    public SpoonacularClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRecipesByIngredients(List<String> ingredients) {
        // convert list of ingredients into comma separated string
        String ingredientsParam = String.join(",", ingredients);

        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.spoonacular.com/recipes/findByIngredients")
                .queryParam("ingredients", ingredientsParam)
                .queryParam("apiKey", "2fc6e4e3981a4b9bb4142a8f627d4e8a")
                .build()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }
}
