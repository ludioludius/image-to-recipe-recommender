package dev.ayush.imagetoreciperecommender.model;

import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientDataTest {

    private IngredientData ingredientData;
    private ClarifaiClient clarifaiClient;
    private DetectedObject detectedObject1;
    private DetectedObject detectedObject2;
    private DetectedObject detectedObject3;

    @BeforeEach
    void setUp() {
        detectedObject1 = new DetectedObject(0.8, "Table");
        detectedObject2 = new DetectedObject(0.2, "Banana");
        detectedObject3 = new DetectedObject(0.9, "Broccoli");
        ingredientData = new IngredientData(Arrays.asList(detectedObject1, detectedObject2));
        clarifaiClient = mock(ClarifaiClient.class);
    }

    @Test
    void testGenerateIngredientList() throws IOException {
        byte[] imageFile = "image data".getBytes();

        // mock API call
        when(clarifaiClient.ObjectsFromImage(imageFile)).thenReturn(Arrays.asList(detectedObject1, detectedObject2, detectedObject3));

        List<String> result = ingredientData.generateIngredientList(clarifaiClient, imageFile);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("Broccoli"));
    }

    @Test
    void testGetIngredientLabels() {
        List<String> labels = Arrays.asList(detectedObject1.getLabel(), detectedObject2.getLabel(), detectedObject3.getLabel());
        List<String> detectedIngredientLabels = ingredientData.getIngredientLabels(labels);
        assertNotNull(detectedIngredientLabels);
        assertEquals(2, detectedIngredientLabels.size());
        assertTrue(detectedIngredientLabels.contains("Banana"));
        assertTrue(detectedIngredientLabels.contains("Broccoli"));
    }


    @Test
    void testGetDetectedObjects() {
        List<DetectedObject> result = ingredientData.getDetectedObjects();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}