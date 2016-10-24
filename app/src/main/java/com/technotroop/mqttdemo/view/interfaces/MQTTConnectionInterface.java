package com.technotroop.mqttdemo.view.interfaces;

/**
 * Created by technotroop on 10/24/16.
 */

public interface MQTTConnectionInterface extends MQTTSubscribeInterface, MQTTPublishInterface, MQTTDisconnectInterface, ConnectionError {

    void onMQTTConnectionSuccess();

    void onMQTTConnectionError(String error);

    void onMessageReceived(String topic, String message);
}
