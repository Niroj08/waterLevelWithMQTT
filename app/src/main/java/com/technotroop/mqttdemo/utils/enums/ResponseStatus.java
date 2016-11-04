package com.technotroop.mqttdemo.utils.enums;

/**
 * Created by technotroop on 11/4/16.
 */

public enum ResponseStatus {
    SUCCESS("1"), ERROR("0");

    private final String response;

    /**
     * @param response
     */
    ResponseStatus(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return response;
    }
}
