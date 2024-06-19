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
import java.util.ArrayList;
import java.util.List;

/*
Controller Class that handles converting an image to a list of detected ingredients (via call to clarifai object detection model),
then responding with a list of recipe's recommended by the spoonacular API's getRecipeByIngredients endpoint
 */
@RestController
@RequestMapping("/recipes")
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

    // Converts an image to a list of recipes by making the appropriate API calls
    @PostMapping
    public ResponseEntity<?> getRecipesFromImage(@RequestParam("image") MultipartFile imageFile) throws IOException {
        // return error if empty file uploaded
        if (imageFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected to upload.");
        }

        // obtain detected ingredients from image file using three calls to appropriate endpoints
        try {
            // extract image as bytes and get detected ingredients
            byte[] imageBytes = imageFile.getBytes();
            System.out.println("File size: " + imageBytes.length);
            List<String> detectedIngredients = ingredientData.generateIngredientList(clarifaiClient, imageBytes);

            // get recipes based on ingredients
            Recipe[] recipes = spoonacularClient.getRecipesByIngredients(detectedIngredients);

            // get full recipe information by calling another spoonacular endpoint
            List<Integer> recipeIds = new ArrayList<Integer>();
            for (Recipe recipe : recipes) {
                recipeIds.add(recipe.getId());
            }
            Recipe[] recipeWithFullInformation = spoonacularClient.getFullRecipeInfoBulk(recipeIds);

            return ResponseEntity.status(HttpStatus.OK).body(recipeWithFullInformation);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API ERROR");
        }
    }
}
