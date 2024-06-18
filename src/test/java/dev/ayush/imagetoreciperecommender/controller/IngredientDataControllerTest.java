package dev.ayush.imagetoreciperecommender.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.ayush.imagetoreciperecommender.ImageToRecipeRecommenderApplication;
import dev.ayush.imagetoreciperecommender.controller.IngredientDataController;
import dev.ayush.imagetoreciperecommender.model.IngredientData;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = ImageToRecipeRecommenderApplication.class)
@WebMvcTest(IngredientDataController.class)
class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClarifaiClient apiClient;

    @MockBean
    private IngredientData ingredientData;

    @Test
    void testBadRequest400() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("image", new byte[]{});

        mockMvc.perform(multipart("/ingredients")
                        .file(emptyFile))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No file selected to upload."));
    }


    @Test
    void testSuccessfulRequest200() throws Exception {
        byte[] imageBytes = "image data".getBytes();
        MockMultipartFile validFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", imageBytes);
        List<String> ingredients = Arrays.asList("Ingredient1", "Ingredient2");

        when(ingredientData.generateIngredientList(apiClient, imageBytes)).thenReturn(ingredients);

        mockMvc.perform(multipart("/ingredients")
                        .file(validFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Ingredient1\",\"Ingredient2\"]"));
    }

    @Test
    void testInternalServerError500() throws Exception {
        byte[] imageBytes = "image data".getBytes();
        MockMultipartFile validFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", imageBytes);

        when(ingredientData.generateIngredientList(apiClient, imageBytes)).thenThrow(new RuntimeException("API Error"));

        mockMvc.perform(multipart("/ingredients")
                        .file(validFile))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("API ERROR"));
    }
}
