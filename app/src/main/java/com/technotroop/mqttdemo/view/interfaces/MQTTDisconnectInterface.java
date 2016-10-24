package com.technotroop.mqttdemo.view.interfaces;

/**
 * Created by technotroop on 10/24/16.
 */
public interface MQTTDisconnectInterface {

    void onMQTTDisconnectSuccess();

    void onMQTTDisconnectError(String error);
}
