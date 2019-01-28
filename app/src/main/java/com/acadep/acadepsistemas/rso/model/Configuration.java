package com.acadep.acadepsistemas.rso.model;

public class Configuration {

    int max_photos;
    int min_photos;

    public Configuration() {
    }

    public Configuration(int max_photos, int min_photos) {
        this.max_photos = max_photos;
        this.min_photos = min_photos;
    }

    public int getMax_photos() {
        return max_photos;
    }

    public void setMax_photos(int max_photos) {
        this.max_photos = max_photos;
    }

    public int getMin_photos() {
        return min_photos;
    }

    public void setMin_photos(int min_photos) {
        this.min_photos = min_photos;
    }
}
