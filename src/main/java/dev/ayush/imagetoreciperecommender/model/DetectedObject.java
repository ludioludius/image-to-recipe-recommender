package dev.ayush.imagetoreciperecommender.model;

/**
 * This class represents an object detected by the object detection API, this does NOT exclude objects that are not
 * ingredients (i.e. does not exclude table, knife etc.).
 */
public class DetectedObject {
    private double probability;
    private String label;

    public DetectedObject(double probability, String label) {
        this.probability = probability;
        this.label = label;
    }

    public double getProbability() {
        return this.probability;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
