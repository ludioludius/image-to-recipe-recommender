package services;

import dev.ayush.imagetoreciperecommender.model.DetectedObject;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClarifaiClientTest {

    private ClarifaiClient clarifaiClient;

    @BeforeEach
    void setUp() {
        clarifaiClient = new ClarifaiClient();
    }

    @Test
    void testAPICall() {

        try{
            Path path = Paths.get("src/test/java/services/carrots_potatoes2.png");
            List<DetectedObject> detectedObjects = clarifaiClient.ObjectsFromImage(Files.readAllBytes(path));
            assertFalse(detectedObjects.isEmpty());
            //assertEquals(detectedObjects, );
        } catch (Exception e){
            e.printStackTrace();
            fail("Error reading the file");
        }
    }
}