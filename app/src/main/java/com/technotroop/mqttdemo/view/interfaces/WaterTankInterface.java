package com.technotroop.mqttdemo.view.interfaces;

import com.technotroop.mqttdemo.service.model.WaterTank;

import java.util.ArrayList;

/**
 * Created by technotroop on 10/27/16.
 */

public interface WaterTankInterface extends ConnectionError {

    void onSuccessGetWaterTank(ArrayList<WaterTank> waterTankList);

    void onErrorGetWaterTank(String error);
}
