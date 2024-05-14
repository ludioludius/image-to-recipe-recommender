package dev.ayush.imagetoreciperecommender.model;

import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// creates the ingredient list as a result of calling the API
@Component
public class IngredientData {
    private List<DetectedObject> ingredientList;

    // Constructor that takes in an API wrapper class and an image to create the data list
    public IngredientData(ClarifaiClient clarifaiClient, String image) throws IOException {
        // Call API wrapper method to get data based on the image
        this.ingredientList = clarifaiClient.ObjectsFromImage(image);
        filterDataList();
    }

    // Method to filter the data list
    public void filterDataList() {
        List<DetectedObject> filteredIngredientList = new ArrayList<>();

        for (DetectedObject detectedObject : ingredientList) {
            if (detectedObject.getProbability() > 0.4) {
                filteredIngredientList.add(detectedObject);
            }
        }

        this.ingredientList = filteredIngredientList;
    }

    public List<DetectedObject> getIngredientList() {
        return this.ingredientList;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}