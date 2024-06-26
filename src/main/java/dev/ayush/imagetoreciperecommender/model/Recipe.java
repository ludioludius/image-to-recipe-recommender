package dev.ayush.imagetoreciperecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    private int id;
    private String title;
    private String image;
    private int servings;
    private String sourceName;
    private String sourceUrl;
    private List<Ingredient> extendedIngredients;
    private String summary;

    // No-argument constructor for Jackson
    public Recipe() {}

    public Recipe(int i, String s, String image, int i1, String s1, String url, List<Ingredient> extendedIngredients, String s2) {
        this.id = i;
        this.title = s;
        this.image = image;
        this.servings = i1;
        this.sourceName = s1;
        this.sourceUrl = url;
        this.extendedIngredients = extendedIngredients;
        this.summary = s2;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public List<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<Ingredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
