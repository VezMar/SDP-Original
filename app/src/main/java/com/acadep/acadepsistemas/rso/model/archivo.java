package com.acadep.acadepsistemas.rso.model;

public class archivo {
    private String type;
    private String src;
    private datatime datatime = new datatime();

    public archivo() {
    }

    public archivo(String type, String src, com.acadep.acadepsistemas.rso.model.datatime datatime) {
        this.type = type;
        this.src = src;
        this.datatime = datatime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public com.acadep.acadepsistemas.rso.model.datatime getDatatime() {
        return datatime;
    }

    public void setDatatime(com.acadep.acadepsistemas.rso.model.datatime datatime) {
        this.datatime = datatime;
    }
}
