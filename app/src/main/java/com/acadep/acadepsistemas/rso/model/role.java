package com.acadep.acadepsistemas.rso.model;

public class role {
    private boolean administrator;

    public role() {
    }

    public role(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
