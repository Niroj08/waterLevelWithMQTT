package com.technotroop.mqttdemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.service.model.WaterTank;
import com.technotroop.mqttdemo.utils.MQTTUtils;
import com.technotroop.mqttdemo.utils.VerticalProgressBar;
import com.technotroop.mqttdemo.utils.enums.WaterLevelTopics;
import com.technotroop.mqttdemo.utils.mqttService.MQTTBaseService;
import com.technotroop.mqttdemo.view.interfaces.MQTTConnectionInterface;

public class WaterLevelActivity extends AppCompatActivity implements MQTTConnectionInterface {


    public SeekBar seekBar;
    private TextView textTankDesc;
    private TextView labelHistory;
    public static VerticalProgressBar progressWaterLevel;

    private Switch switchDeviceStatus;

    private MQTTBaseService mqttBaseService;

    private WaterTank waterTank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level);

        mqttBaseService = new MQTTBaseService(this);

        waterTank = (WaterTank) getIntent().getSerializableExtra("waterTankDetails");

        textTankDesc = (TextView) findViewById(R.id.textTankDesc);
        labelHistory = (TextView) findViewById(R.id.labelHistory);
        switchDeviceStatus = (Switch) findViewById(R.id.switchDeviceStatus);
        progressWaterLevel = (VerticalProgressBar) findViewById(R.id.progressWater);

        labelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchActivity(WaterLevelHistory.class);
            }
        });
        textTankDesc.setText("Type: " + waterTank.getType() + "\nHeight: " + waterTank.getHeight() + "\nVolume: " + waterTank.getVolume());
        switchDeviceStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mqttBaseService.publishMessage(WaterLevelTopics.ON_OFF.toString(), "ON");
                } else {

                    mqttBaseService.publishMessage(WaterLevelTopics.ON_OFF.toString(), "OFF");
                }
            }
        });

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        progressWaterLevel.setProgress(MQTTUtils.getRetainedWaterLevel());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    progressWaterLevel.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onMQTTConnectionSuccess() {

    }

    @Override
    public void onMQTTConnectionError(String error) {

    }

    @Override
    public void onMessageReceived(String topic, String message) {

        if (topic.equalsIgnoreCase(WaterLevelTopics.SEEK.toString())
                || topic.equalsIgnoreCase(WaterLevelTopics.WATER_LEVEL.toString())) {
            int waterLevel = Integer.parseInt(message);

            MQTTUtils.storeRetainedWaterLevel(waterLevel);

            if (progressWaterLevel != null) {
                progressWaterLevel.setProgress(waterLevel);
            }
        } else if (topic.equalsIgnoreCase(WaterLevelTopics.DEVICE_STATUS.toString())
                || topic.equalsIgnoreCase(WaterLevelTopics.ON_OFF.toString())) {

            if (message.equalsIgnoreCase("on")) {

                switchDeviceStatus.setChecked(true);
            } else if (message.equalsIgnoreCase("off")) {
                switchDeviceStatus.setChecked(false);
            }
        }
    }

    @Override
    public void onErrorNoConnection() {

    }

    @Override
    public void onMQTTDisconnectSuccess() {

    }

    @Override
    public void onMQTTDisconnectError(String error) {

    }

    @Override
    public void onMQTTPublishNoTopicError() {

    }

    @Override
    public void onMQTTPublishNoMessageError() {

    }

    @Override
    public void onMQTTPublishSuccess() {

    }

    @Override
    public void onMQTTPublishError(String error) {

    }

    @Override
    public void onMQTTSubscribeNoTopicError() {

    }

    @Override
    public void onMQTTSubscriptionSuccess() {

    }

    @Override
    public void onMQTTSubscriptionError(String error) {

    }

    private void launchActivity(Class calledClass) {
        Intent intent = new Intent(this, calledClass);
        intent.putExtra("waterTankId", waterTank.getId());
        startActivity(intent);
    }
}
