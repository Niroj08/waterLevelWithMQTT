package com.technotroop.mqttdemo.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevel extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;
    @SerializedName("water_level")
    private float waterLevel;

    //x-value is the parameter for bar chart could be hour, day, month
    @SerializedName("x-value")
    private float xValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(float waterLevel) {
        this.waterLevel = waterLevel;
    }

    public float getxValue() {
        return xValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }
}
