package dev.ayush.imagetoreciperecommender.model;

public class DetectedObject {
    private int probability;
    private String label;

    public DetectedObject(int probability, String label) {
        this.probability = probability;
        this.label = label;
    }

    public int getProbability() {
        return probability;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }
}
