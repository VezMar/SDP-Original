package com.acadep.acadepsistemas.rso.model;

import java.util.List;

public class Configuration {

    List<Activity_types> activitys_types;
    int max_photos;
    int min_photos;

    public Configuration() {
    }

    public Configuration(List<Activity_types> activitys_types, int max_photos, int min_photos) {
        this.activitys_types = activitys_types;
        this.max_photos = max_photos;
        this.min_photos = min_photos;
    }

    public List<Activity_types> getActivitys_types() {
        return activitys_types;
    }

    public void setActivitys_types(List<Activity_types> activitys_types) {
        this.activitys_types = activitys_types;
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
