package com.technotroop.mqttdemo.view.interfaces;

/**
 * Created by technotroop on 10/28/16.
 */
public interface WaterLevelHistoryInterface extends ConnectionError{

    void onSuccessGetWaterTankHistory(long refValue);

    void onErrorGetWaterTankHistory(String error);

    void noWaterLevelHistoryToShow();
}
