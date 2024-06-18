package dev.ayush.imagetoreciperecommender.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import dev.ayush.imagetoreciperecommender.model.Ingredient;
import dev.ayush.imagetoreciperecommender.model.IngredientData;
import dev.ayush.imagetoreciperecommender.model.Recipe;
import dev.ayush.imagetoreciperecommender.services.ClarifaiClient;
import dev.ayush.imagetoreciperecommender.services.SpoonacularClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RecipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClarifaiClient clarifaiClient;

    @Mock
    private IngredientData ingredientData;

    @Mock
    private SpoonacularClient spoonacularClient;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void testGetRecipesFromImage_Success() throws Exception {
        // Mocking dependencies
        byte[] imageBytes = "test image content".getBytes();
        List<String> ingredients = Arrays.asList("tomato", "cheese");

        List<Ingredient> extendedIngredients = Arrays.asList(new Ingredient("tomato"), new Ingredient("cheese"));

        Recipe[] recipes = {
                new Recipe(1, "Recipe 1", "image1.jpg", 4, "Source 1", "http://source1.com", extendedIngredients, "Summary 1"),
                new Recipe(2, "Recipe 2", "image2.jpg", 2, "Source 2", "http://source2.com", extendedIngredients, "Summary 2")
        };

        Recipe[] fullRecipes = {
                new Recipe(1, "Recipe 1 Full", "image1_full.jpg", 4, "Source 1", "http://source1.com/full", extendedIngredients, "Full Summary 1"),
                new Recipe(2, "Recipe 2 Full", "image2_full.jpg", 2, "Source 2", "http://source2.com/full", extendedIngredients, "Full Summary 2")
        };

        when(ingredientData.generateIngredientList(any(ClarifaiClient.class), any(byte[].class)))
                .thenReturn(ingredients);
        when(spoonacularClient.getRecipesByIngredients(ingredients)).thenReturn(recipes);
        when(spoonacularClient.getFullRecipeInfoBulk(any(List.class))).thenReturn(fullRecipes);

        // Creating a mock image file
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.jpg",
                MediaType.IMAGE_JPEG_VALUE, imageBytes);

        // Performing the POST request
        mockMvc.perform(multipart("/recipes")
                        .file(mockMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesFromImage_NoFile() throws Exception {
        mockMvc.perform(multipart("/recipes")
                        .file(new MockMultipartFile("image", "", MediaType.IMAGE_JPEG_VALUE, new byte[0])))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRecipesFromImage_ApiError() throws Exception {
        byte[] imageBytes = "test image content".getBytes();

        when(ingredientData.generateIngredientList(any(ClarifaiClient.class), any(byte[].class)))
                .thenThrow(new RuntimeException("API ERROR"));

        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.jpg",
                MediaType.IMAGE_JPEG_VALUE, imageBytes);

        mockMvc.perform(multipart("/recipes")
                        .file(mockMultipartFile))
                .andExpect(status().isInternalServerError());
    }
}
