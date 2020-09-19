package com.zhangct.example.model;

import java.util.ArrayList;

public class DeploymentModel {
    private String batch;
    private Integer type;
    private ArrayList<SystemModel> systems;

    @Override
    public String toString() {
        return "DeploymentModel{" +
                "batch='" + batch + '\'' +
                ", type=" + type +
                ", systems=" + systems +
                '}';
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ArrayList<SystemModel> getSystems() {
        return systems;
    }

    public void setSystems(ArrayList<SystemModel> systems) {
        this.systems = systems;
    }
}
