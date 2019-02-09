package com.acadep.acadepsistemas.rso.model;

public class Activity_types {
    String activity_name;
    boolean after;
    boolean before;
    boolean during;

    public Activity_types() {
    }

    public Activity_types(String activity_name, boolean after, boolean before, boolean during) {
        this.activity_name = activity_name;
        this.after = after;
        this.before = before;
        this.during = during;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
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
