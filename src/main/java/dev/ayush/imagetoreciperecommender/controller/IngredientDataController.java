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
@RequestMapping("/ingredients")
public class IngredientDataController {

    // field and constructor for clarifai client
    private final ClarifaiClient apiClient;

    public IngredientDataController(ClarifaiClient apiClient) {
        this.apiClient = apiClient;
    }

    // http:
    @PostMapping
    public ResponseEntity<String> getIngredientsFromImage(@RequestParam("image") MultipartFile imageFile) throws IOException {
        byte[] imageBytes;

        // return error if empty file uploaded
        if (imageFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected to upload.");
        }

        try {
            // Get the file bytes and store them in memory
            imageBytes = imageFile.getBytes();
            // call user code to generate objects from image and return objects in the body
            System.out.println("File size: " + imageBytes.length);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully and stored in memory.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }

        // return new IngredientData(apiClient).toString();
    }

    // wish list: new method in Ingredient data class that
}
