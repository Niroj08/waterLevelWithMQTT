package com.technotroop.mqttdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.technotroop.mqttdemo.utils.mqttService.MQTTBaseService;

/**
 * Created by technotroop on 10/21/16.
 */

public class MQTTApplicaiton extends Application {

    private static MQTTApplicaiton instance;

    public MQTTApplicaiton() {
        instance = this;

    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // startMQQTTBaseService();
    }

    public static void startMQQTTBaseService() {
        Intent serviceIntent = new Intent(getContext(), MQTTBaseService.class);
        getContext().startService(serviceIntent);
    }
}
