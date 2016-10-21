package com.technotroop.mqttdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.technotroop.mqttdemo.MQTTApplicaiton;

/**
 * Created by technotroop on 10/21/16.
 */
public class MQTTUtils {

    public static Context getContext() {
        return MQTTApplicaiton.getContext();
    }

    public static void storeRetainedWaterLevel(int waterLevel) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.MQTT_RETAINED_WATERLEVEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("waterLevel", waterLevel);
        editor.commit();
    }

    public static int getRetainedWaterLevel() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.MQTT_RETAINED_WATERLEVEL, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("waterLevel", 0);
    }

    public static boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
