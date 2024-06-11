package dev.ayush.imagetoreciperecommender.controller;

import dev.ayush.imagetoreciperecommender.model.IngredientData;
import dev.ayush.imagetoreciperecommender.model.Recipe;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import dev.ayush.imagetoreciperecommender.services.SpoonacularClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recipes")  // http:localhost:8080/recipes
@CrossOrigin()
public class RecipeController {

    private final ClarifaiClient clarifaiClient;
    private final IngredientData ingredientData;
    private final SpoonacularClient spoonacularClient;

    // Spring framework calls this constructor, uses dependency injection to inject dependencies
    public RecipeController(ClarifaiClient clarifaiClient, IngredientData ingredientData, SpoonacularClient spoonacularClient) {
        this.clarifaiClient = clarifaiClient;
        this.ingredientData = ingredientData;
        this.spoonacularClient = spoonacularClient;
    }


    @PostMapping
    public ResponseEntity<?> getRecipesFromImage(@RequestParam("image") MultipartFile imageFile) throws IOException {

        // return error if empty file uploaded
        if (imageFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected to upload.");
        }

        // obtain detected ingredients from image file using API
        try {
            byte[] imageBytes = imageFile.getBytes();
            System.out.println("File size: " + imageBytes.length);
            List<String> detectedIngredients = ingredientData.generateIngredientList(clarifaiClient, imageBytes);

            // TODO: format call to spoonacular API
            Recipe[] recipes = spoonacularClient.getRecipesByIngredients(detectedIngredients);

            return ResponseEntity.status(HttpStatus.OK).body(recipes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API ERROR"); // TODO: UPDATE THIS ERROR MESSAGE< DOENST REPRESENT APP ERROR
        }
    }
}