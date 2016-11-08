package com.technotroop.mqttdemo.view.interfaces;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * Created by technotroop on 10/28/16.
 */
public interface WaterLevelHistoryInterface extends ConnectionError {

    void onSuccessGetWaterTankHistory(long refValue, List<Entry> entries);

    void onErrorGetWaterTankHistory(String error);

    void noWaterLevelHistoryToShow();
}
