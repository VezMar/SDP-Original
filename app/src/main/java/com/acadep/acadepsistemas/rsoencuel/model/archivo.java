package com.acadep.acadepsistemas.rsoencuel.model;

public class archivo {
    private String type;
    private String src;
    private datetime datatime = new datetime();

    public archivo() {
    }

    public archivo(String type, String src, datetime datatime) {
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

    public datetime getDatatime() {
        return datatime;
    }

    public void setDatatime(datetime datatime) {
        this.datatime = datatime;
    }
}
