package com.technotroop.mqttdemo.utils.enums;

/**
 * Created by technotroop on 10/24/16.
 */

public enum WaterLevelTopics {

    WATER_LEVEL("aj_mjn/feeds/water-level"), ERROR_STATUS("aj_mjn/feeds/error_status"), DEVICE_STATUS("aj_mjn/feeds/device_status"), SEEK("aj_mjn/feeds/seek"), ON_OFF("aj_mjn/feeds/onoff");

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
