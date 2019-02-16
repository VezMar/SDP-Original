package com.acadep.acadepsistemas.rso.model;

public class Event_types {
    String name;
    boolean after;
    boolean before;
    boolean during;

    public Event_types() {
    }

    public Event_types(String activity_name, boolean after, boolean before, boolean during) {
        this.name = activity_name;
        this.after = after;
        this.before = before;
        this.during = during;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAfter() {
        return after;
    }

    public void setAfter(boolean after) {
        this.after = after;
    }

    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }

    public boolean isDuring() {
        return during;
    }

    public void setDuring(boolean during) {
        this.during = during;
    }
}
