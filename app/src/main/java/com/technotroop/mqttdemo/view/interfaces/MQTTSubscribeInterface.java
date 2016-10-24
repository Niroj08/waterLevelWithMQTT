package com.technotroop.mqttdemo.view.interfaces;

/**
 * Created by technotroop on 10/24/16.
 */
public interface MQTTSubscribeInterface {

    void onMQTTSubscribeNoTopicError();

    void onMQTTSubscriptionSuccess();

    void onMQTTSubscriptionError(String error);
}
