package dev.ayush.imagetoreciperecommender.services;

import dev.ayush.imagetoreciperecommender.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.net.URI;

/*
Includes methods for handling communication with the spoonacular API
 */
@Component
public class SpoonacularClient {

    private final RestTemplate restTemplate;

    @Autowired
    public SpoonacularClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // call the getRecipeByIngredients endpoint of the spoonacular service
    public Recipe[] getRecipesByIngredients(List<String> ingredients) {
        // convert list of ingredients into comma separated string
        String ingredientsParam = String.join(",", ingredients);

        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.spoonacular.com/recipes/findByIngredients")
                .queryParam("ingredients", ingredientsParam)
                .queryParam("apiKey", "2fc6e4e3981a4b9bb4142a8f627d4e8a")
                .build()
                .toUri();

        return restTemplate.getForObject(uri, Recipe[].class);
    }

    // call to the getRecipeInfoBulk endpoint of the spoonacular service
    public Recipe[] getFullRecipeInfoBulk(List<Integer> recipeIds) {
        // Convert List<Integer> to a comma-separated String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recipeIds.size(); i++) {
            sb.append(recipeIds.get(i));
            if (i < recipeIds.size() - 1) {
                sb.append(",");
            }
        }

        String recipeIdsString = sb.toString();
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.spoonacular.com/recipes/informationBulk")
                .queryParam("ids", recipeIdsString)
                .queryParam("apiKey", "2fc6e4e3981a4b9bb4142a8f627d4e8a")
                .build()
                .toUri();

        return restTemplate.getForObject(uri, Recipe[].class);
    }
}
