package com.technotroop.mqttdemo.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.controller.WaterLevelController;
import com.technotroop.mqttdemo.service.model.WaterLevel;
import com.technotroop.mqttdemo.utils.XValueFormatter;

import java.util.UUID;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevelHistory extends Activity {

    private WaterLevelController waterLevelController;

    private BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterlevel_history);

        barChart = (BarChart) findViewById(R.id.barChart);
        waterLevelController = new WaterLevelController();

        for (int i = 0; i < 10; i++) {
            WaterLevel waterLevel = new WaterLevel();
            waterLevel.setId(String.valueOf(UUID.randomUUID()));
            float value = 10.8f * i;
            waterLevel.setWaterLevel(value);
            waterLevel.setxValue(i);

            waterLevelController.storeWaterLevel(waterLevel);
        }

        BarData data = new BarData(waterLevelController.getBarChartDataForWaterLevel());

        Description desc = new Description();
        desc.setText("Water Level history for: ");

        barChart.setPinchZoom(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(2000);
        barChart.setDescription(desc);
        barChart.setData(data);
        barChart.invalidate();
    }
}
