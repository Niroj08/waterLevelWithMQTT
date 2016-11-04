package com.technotroop.mqttdemo.controller;

import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.technotroop.mqttdemo.service.api.WaterLevelHistoryService;
import com.technotroop.mqttdemo.service.data.WaterLevelDataService;
import com.technotroop.mqttdemo.service.model.WaterLevel;
import com.technotroop.mqttdemo.utils.Constants;
import com.technotroop.mqttdemo.utils.enums.ResponseStatus;
import com.technotroop.mqttdemo.view.interfaces.WaterLevelHistoryInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevelController {

    public WaterLevelHistoryService waterLevelHistoryService = new WaterLevelHistoryService();
    private WaterLevelHistoryInterface waterLevelHistoryInterface;

    public WaterLevelController(WaterLevelHistoryInterface waterLevelHistoryInterface){
        this.waterLevelHistoryInterface = waterLevelHistoryInterface;
    }

    public void getWaterTankHistory(String waterTankId) {
        waterLevelHistoryService.waterTankHistory(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    ResponseBody responseBody = response.body();
                    ResponseBody errorBody = response.errorBody();
                    if (responseBody != null && errorBody == null) {

                        try {
                            JSONObject responseObject = new JSONObject(responseBody.string());
                            if (responseObject.optString("status").equalsIgnoreCase(String.valueOf(ResponseStatus.SUCCESS))) {

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {

                } else {


                }
            }
        }, waterTankId);
    }

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