package dev.ayush.imagetoreciperecommender.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetectedObjectTest {

    @Test
    void testConstructorAndGetters() {
        DetectedObject detectedObject = new DetectedObject(0.75, "Apple");

        assertEquals(0.75, detectedObject.getProbability());
        assertEquals("Apple", detectedObject.getLabel());
    }

    @Test
    void testSetProbability() {
        DetectedObject detectedObject = new DetectedObject(0.75, "Apple");
        detectedObject.setProbability(0.85);

        assertEquals(0.85, detectedObject.getProbability());
    }

    @Test
    void testSetLabel() {
        DetectedObject detectedObject = new DetectedObject(0.75, "Apple");
        detectedObject.setLabel("Banana");

        assertEquals("Banana", detectedObject.getLabel());
    }
}
