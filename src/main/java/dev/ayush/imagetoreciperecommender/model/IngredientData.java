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
 * by removing low probability detections and removing not food detections. The object detection API is called to
 * generate an initial unfiltered list of detected objects before filtering.
 */
@Component
public class IngredientData {
    private List<DetectedObject> detectedObjects;

    public IngredientData(List<DetectedObject> detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    // method takes in clarifaiClient and image bytes to populate the ingredient list
    public List<String> generateIngredientList(ClarifaiClient clarifaiClient, byte[] imageFile) throws IOException {

        this.detectedObjects = clarifaiClient.ObjectsFromImage(imageFile);
        this.filterDataList(); // remove low probability detections
        List<String> imageLabels =  this.getLabels();
        // TODO: new function to remove unwanted objects (ie objects that are not food items) ????
        return getIngredientLabels(imageLabels);
    }

    public List<String> getIngredientLabels(List<String> imageLabels) {
        List<String> filteredLabels = new ArrayList<>();
        for (String label : imageLabels) {
            if (ClarifaiClient.detectableIngredients.contains(label)) {
                filteredLabels.add(label);
            }
        }
        return filteredLabels;
    }

    public List<String> getLabels(){
        // create list of labels, i.e discard probability
        List<String> labels = new ArrayList<String>();
        for (DetectedObject detectedObject : this.detectedObjects) {
           labels.add(detectedObject.getLabel());
        }

        // remove duplicates
        Set<String> labelsSet = new HashSet<>(labels);
        return new ArrayList<>(labelsSet);
    }


    // Method to filter the data list
    public void filterDataList() {
        // null guard
        if (this.detectedObjects == null) {
            this.detectedObjects = new ArrayList<>();
        }

        // remove low probability detections
        List<DetectedObject> filteredIngredientList = new ArrayList<>();
        for (DetectedObject detectedObject : detectedObjects) {
            if (detectedObject.getProbability() > 0.4) {
                filteredIngredientList.add(detectedObject);
            }
        }
        this.detectedObjects = filteredIngredientList;
    }

    public List<DetectedObject> getDetectedObjects() {
        return this.detectedObjects;
    }
}