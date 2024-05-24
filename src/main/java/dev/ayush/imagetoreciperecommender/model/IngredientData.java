package dev.ayush.imagetoreciperecommender.model;

import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class stores the list of ingredients detected in the image, it includes methods for creating that list
 * by removing low probability detections and removing not food detections.
 */
@Component
public class IngredientData {
    private List<DetectedObject> ingredientList;

    public IngredientData(List<DetectedObject> ingredientList) {
        this.ingredientList = ingredientList;
    }

    // method takes in clarifaiClient and image bytes to populate the ingredient list
    public List<String> generateIngredientList(ClarifaiClient clarifaiClient, byte[] imageFile) throws IOException {
        this.ingredientList = clarifaiClient.ObjectsFromImage(imageFile);
        filterDataList(); // remove low probability detections
        // TODO: new function to remove unwanted objects (ie objects that are not food items) ????
        return this.getLabels();
    }

    public List<String> getLabels(){
        // create list of labels, i.e discard probability
        List<String> labels = new ArrayList<String>();
        for (DetectedObject detectedObject : this.ingredientList) {
           labels.add(detectedObject.getLabel());
        }

        // remove duplicates
        Set<String> labelsSet = new HashSet<>(labels);
        return new ArrayList<>(labelsSet);
    }


    // Method to filter the data list
    public void filterDataList() {
        // null guard
        if (this.ingredientList == null) {
            this.ingredientList = new ArrayList<>();
        }

        // remove low probability detections
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
}