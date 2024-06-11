package services;

import dev.ayush.imagetoreciperecommender.model.DetectedObject;
import dev.ayush.imagetoreciperecommender.model.Recipe;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import dev.ayush.imagetoreciperecommender.services.SpoonacularClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpoonacularClientTest {

    private SpoonacularClient spoonacularClient;

    @BeforeEach
    void setUp() {
        spoonacularClient = new SpoonacularClient(new RestTemplate());
    }

    @Test
    void testAPICall() {

        try{
            List<String> ingredients = Arrays.asList("Potato", "Carrot");
            Recipe[] recipes = spoonacularClient.getRecipesByIngredients(ingredients);
            assertNotNull(recipes);
            System.out.println(recipes);
        } catch (Exception e){
            e.printStackTrace();
            fail("Error reading the file");
        }
    }
}