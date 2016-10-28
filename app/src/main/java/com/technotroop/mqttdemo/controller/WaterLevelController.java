package com.technotroop.mqttdemo.controller;

import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.technotroop.mqttdemo.service.data.WaterLevelDataService;
import com.technotroop.mqttdemo.service.model.WaterLevel;

import io.realm.RealmResults;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevelController {


    public void storeWaterLevel(WaterLevel waterLevel) {
        WaterLevelDataService.saveWaterLevel(waterLevel);
    }

    public RealmResults<WaterLevel> getWaterLevel() {
        return WaterLevelDataService.getWaterLevel();
    }

    public RealmBarDataSet<WaterLevel> getBarChartDataForWaterLevel() {
        return new RealmBarDataSet<WaterLevel>(getWaterLevel(), "xValue", "waterLevel");
    }
}