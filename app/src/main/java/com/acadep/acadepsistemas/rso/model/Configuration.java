package com.acadep.acadepsistemas.rso.model;

import java.util.List;

public class Configuration {

    List<Event_types> event_types;
    int max_photos;
    int min_photos;
    List<String> materials;

    public Configuration() {
    }

//    public Configuration(List<Event_types> event_types, int max_photos, int min_photos) {
//        this.event_types = event_types;
//        this.max_photos = max_photos;
//        this.min_photos = min_photos;
//    }


    public Configuration(List<Event_types> activitys_types, int max_photos, int min_photos, List<String> materials) {
        this.event_types = activitys_types;
        this.max_photos = max_photos;
        this.min_photos = min_photos;
        this.materials = materials;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public List<Event_types> getEvent_types() {
        return event_types;
    }

    public void setEvent_types(List<Event_types> event_types) {
        this.event_types = event_types;
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
