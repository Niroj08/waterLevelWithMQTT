package com.technotroop.mqttdemo.service.model;

/**
 * Created by technotroop on 10/24/16.
 */

public class WaterTank {

    private String brandname;
    private String volume;

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
