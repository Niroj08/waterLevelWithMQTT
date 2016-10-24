package com.technotroop.mqttdemo.view.interfaces;

/**
 * Created by technotroop on 10/24/16.
 */
public interface MQTTPublishInterface {

    void onMQTTPublishNoTopicError();

    void onMQTTPublishNoMessageError();

    void onMQTTPublishSuccess();

    void onMQTTPublishError(String error);
}
