package com.technotroop.mqttdemo.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.controller.WaterLevelController;
import com.technotroop.mqttdemo.service.model.WaterLevel;
import com.technotroop.mqttdemo.utils.HourAxisValueFormatter;
import com.technotroop.mqttdemo.utils.MQTTUtils;
import com.technotroop.mqttdemo.view.interfaces.WaterLevelHistoryInterface;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevelHistory extends Activity implements WaterLevelHistoryInterface {


    private ProgressBar progressBar;
    private WaterLevelController waterLevelController;

    private BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterlevel_history);

        progressBar = (ProgressBar) findViewById(R.id.progressBarWaterLevelHistory);

        barChart = (BarChart) findViewById(R.id.barChart);
        waterLevelController = new WaterLevelController(this);

        String waterTankId = getIntent().getStringExtra("waterTankId");
        if (!TextUtils.isEmpty(waterTankId)) {

            UIOnServerCall();
            waterLevelController.getWaterTankHistory(waterTankId);
        } else {

        }
    }

    @Override
    public void onSuccessGetWaterTankHistory(long refValue) {
        UIOnServerResponse();

        RealmBarDataSet<WaterLevel> dataSet = waterLevelController.getBarChartDataForWaterLevel();

        BarData data = new BarData(dataSet);
        data.setBarWidth(5);

        Description desc = new Description();
        desc.setText("Water Level history for: ");

        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(refValue);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(xAxisFormatter);

        barChart.setPinchZoom(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(2000);
        barChart.setDescription(desc);
        barChart.setData(data);

    }

    @Override
    public void onErrorGetWaterTankHistory(String error) {

        UIOnServerResponse();

    }

    @Override
    public void noWaterLevelHistoryToShow() {

        UIOnServerResponse();
    }

    @Override
    public void onErrorNoConnection() {

        UIOnServerResponse();

    }

    private void UIOnServerResponse() {

        progressBar.setVisibility(View.GONE);
        MQTTUtils.enableUserInteraction(getWindow());
    }

    private void UIOnServerCall() {

        progressBar.setVisibility(View.VISIBLE);
        MQTTUtils.disableUserInteraction(getWindow());
    }
}
