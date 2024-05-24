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

    @BeforeEach
    void setUp() {
        detectedObject1 = new DetectedObject(0.8, "Apple");
        detectedObject2 = new DetectedObject(0.2, "Banana");
        ingredientData = new IngredientData(Arrays.asList(detectedObject1, detectedObject2));
        clarifaiClient = mock(ClarifaiClient.class);
    }

    @Test
    void testGenerateIngredientList() throws IOException {
        byte[] imageFile = "image data".getBytes();
        when(clarifaiClient.ObjectsFromImage(imageFile)).thenReturn(Arrays.asList(detectedObject1, detectedObject2));

        List<String> result = ingredientData.generateIngredientList(clarifaiClient, imageFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("Apple"));
    }

    @Test
    void testGetLabels() {
        List<String> labels = ingredientData.getLabels();
        assertNotNull(labels);
        assertEquals(2, labels.size());
        assertTrue(labels.contains("Apple"));
        assertTrue(labels.contains("Banana"));
    }

    @Test
    void testFilterDataList() {
        ingredientData.filterDataList();
        List<DetectedObject> filteredList = ingredientData.getIngredientList();
        assertNotNull(filteredList);
        assertEquals(1, filteredList.size());
        assertEquals("Apple", filteredList.get(0).getLabel());
    }

    @Test
    void testGetIngredientList() {
        List<DetectedObject> result = ingredientData.getIngredientList();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}