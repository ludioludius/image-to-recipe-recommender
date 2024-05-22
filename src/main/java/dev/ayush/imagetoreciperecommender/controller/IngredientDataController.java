package dev.ayush.imagetoreciperecommender.controller;

import dev.ayush.imagetoreciperecommender.model.IngredientData;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController // tells spring that this a rest controller class
@RequestMapping("/ingredients")
public class IngredientDataController {

    // field and constructor for clarifai client
    private final ClarifaiClient apiClient;

    public IngredientDataController(ClarifaiClient apiClient) {
        this.apiClient = apiClient;
    }

    // http:
    @GetMapping
    public String getIngredientsFromImage() throws IOException {
        return new IngredientData(apiClient).toString();
    }
}
