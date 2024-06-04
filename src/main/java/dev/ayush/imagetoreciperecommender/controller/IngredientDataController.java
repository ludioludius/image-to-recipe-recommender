package dev.ayush.imagetoreciperecommender.controller;

import dev.ayush.imagetoreciperecommender.model.IngredientData;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@RestController // tells spring that this a rest controller class
@RequestMapping("/ingredients")  // http:localhost:8080/ingredients
@CrossOrigin()
public class IngredientDataController {

    private final ClarifaiClient apiClient;
    private final IngredientData ingredientData;

    // Spring framework calls this constructor, uses dependency injection to inject dependencies
    public IngredientDataController(ClarifaiClient apiClient, IngredientData ingredientData) {
        this.apiClient = apiClient;
        this.ingredientData = ingredientData;
    }

    @PostMapping
    public ResponseEntity<?> getIngredientsFromImage(@RequestParam("image") MultipartFile imageFile) throws IOException {

        // return error if empty file uploaded
        if (imageFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected to upload.");
        }

        // obtain detected ingredients from image file using API
        try {
            byte[] imageBytes = imageFile.getBytes();
            System.out.println("File size: " + imageBytes.length);
            List<String> detectedIngredients = ingredientData.generateIngredientList(apiClient, imageBytes);
            return ResponseEntity.status(HttpStatus.OK).body(detectedIngredients);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API ERROR"); // TODO: UPDATE THIS ERROR MESSAGE< DOENST REPRESENT APP ERROR
        }
    }
}
