package com.technotroop.mqttdemo.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by technotroop on 10/24/16.
 */

public class WaterTank implements Serializable {

    private String id;
    @SerializedName("brand_name")
    private String brandName;
    private String volume;
    private String height;
    private String type;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
