package com.technotroop.mqttdemo.utils.mqttService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.technotroop.mqttdemo.MQTTApplicaiton;

/**
 * Created by technotroop on 10/20/16.
 */

public class MQTTServiceStarterAtBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
          //  MQTTApplicaiton.startMQQTTBaseService();
        }
    }
}
