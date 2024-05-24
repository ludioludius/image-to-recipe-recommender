package dev.ayush.imagetoreciperecommender;

import dev.ayush.imagetoreciperecommender.controller.IngredientDataController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationSmokeTest {

    // implicit autowired (i.e. dependency injection)
    private IngredientDataController ingredientDataController;

    // smoke test to ensure spring application context is not null
    @Test
    void contextLoads() {
        assertThat(ingredientDataController).isNotNull();
    }

}
