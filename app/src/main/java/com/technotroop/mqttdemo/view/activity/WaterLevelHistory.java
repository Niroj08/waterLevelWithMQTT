package com.technotroop.mqttdemo.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.controller.WaterLevelController;
import com.technotroop.mqttdemo.utils.HourAxisValueFormatter;
import com.technotroop.mqttdemo.utils.MQTTUtils;
import com.technotroop.mqttdemo.view.interfaces.WaterLevelHistoryInterface;

import java.util.List;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevelHistory extends Activity implements WaterLevelHistoryInterface {


    private ProgressBar progressBar;
    private WaterLevelController waterLevelController;

    private LineChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterlevel_history);

        progressBar = (ProgressBar) findViewById(R.id.progressBarWaterLevelHistory);

        barChart = (LineChart) findViewById(R.id.barChart);
        waterLevelController = new WaterLevelController(this);

        String waterTankId = getIntent().getStringExtra("waterTankId");
        if (!TextUtils.isEmpty(waterTankId)) {

            UIOnServerCall();
            waterLevelController.getWaterTankHistory(waterTankId);
        } else {

        }
    }

    @Override
    public void onSuccessGetWaterTankHistory(long refValue, List<Entry> entries) {
        UIOnServerResponse();

        // RealmBarDataSet<WaterLevel> dataSet = waterLevelController.getBarChartDataForWaterLevel();

        LineDataSet dataSet = new LineDataSet(entries, "Water Level History for: ");
        dataSet.setColor(getResources().getColor(R.color.colorPrimaryDark));
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setFillColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setDrawFilled(true);

        Description desc = new Description();
        desc.setText("");

        LineData data = new LineData(dataSet);

        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(refValue);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(xAxisFormatter);

        barChart.setPinchZoom(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(2000);
        barChart.setData(data);
        barChart.setDescription(desc);
        barChart.invalidate();

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
