package com.technotroop.mqttdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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

    public static void disableUserInteraction(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void enableUserInteraction(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    public static void showSnackBar(String message, View view) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
