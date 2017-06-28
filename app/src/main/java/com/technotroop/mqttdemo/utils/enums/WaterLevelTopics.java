package com.technotroop.mqttdemo.utils.enums;

/**
 * Created by technotroop on 10/24/16.
 */

public enum WaterLevelTopics {

    WATER_LEVEL("feeds/water-level"),
    ERROR_STATUS("feeds/error_status"),
    DEVICE_STATUS("feeds/device_status"),
    SEEK("feeds/seek"),
    ON_OFF("feeds/onoff");

    private final String topics;

    /**
     * @param topics
     */
    WaterLevelTopics(String topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return topics;
    }
}
