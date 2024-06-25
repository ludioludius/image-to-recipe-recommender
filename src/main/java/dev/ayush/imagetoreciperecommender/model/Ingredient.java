package dev.ayush.imagetoreciperecommender.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private String name;

    @JsonCreator
    public Ingredient(@JsonProperty("name") String name) {
        this.name = name;
    }


    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
