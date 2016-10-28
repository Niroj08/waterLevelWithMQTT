package com.technotroop.mqttdemo.view.interfaces;

import com.technotroop.mqttdemo.view.activity.WaterLevelHistory;

import java.util.ArrayList;

/**
 * Created by technotroop on 10/28/16.
 */
public interface WaterLevelHistoryInterface {

    void onSuccessGetWaterTankHistory(ArrayList<WaterLevelHistory> waterLevelHistoryList);

    void onErrorGetWaterTankHistory(String error);
}
