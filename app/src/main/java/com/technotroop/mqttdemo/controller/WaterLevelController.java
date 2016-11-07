package com.technotroop.mqttdemo.controller;

import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.google.gson.Gson;
import com.technotroop.mqttdemo.service.api.WaterLevelHistoryService;
import com.technotroop.mqttdemo.service.data.WaterLevelDataService;
import com.technotroop.mqttdemo.service.model.WaterLevel;
import com.technotroop.mqttdemo.utils.Constants;
import com.technotroop.mqttdemo.utils.enums.ResponseStatus;
import com.technotroop.mqttdemo.view.interfaces.WaterLevelHistoryInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
    private WaterLevel waterLevel;

    public WaterLevelController(WaterLevelHistoryInterface waterLevelHistoryInterface) {
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

                                Gson gson = new Gson();
                                JSONArray data = responseObject.optJSONArray("data");

                                if (data.length() > 0) {

                                    long refValue = convertDateToTimeStampSeconds(data.optJSONObject(0).optString("created_at"));

                                    for (int i = 0; i < data.length(); i++) {
                                        waterLevel = new WaterLevel();
                                        JSONObject waterLevelObject = data.getJSONObject(i);

                                        waterLevel = gson.fromJson(String.valueOf(waterLevelObject), WaterLevel.class);

                                        long xValue = convertDateToTimeStampSeconds(waterLevelObject.optString("created_at"));

                                        waterLevel.setxValue((xValue - refValue) / 1000);

                                        storeWaterLevel(waterLevel);
                                    }

                                    waterLevelHistoryInterface.onSuccessGetWaterTankHistory(refValue);
                                } else {

                                    waterLevelHistoryInterface.onSuccessGetWaterTankHistory(0);
                                    //waterLevelHistoryInterface.noWaterLevelHistoryToShow();
                                }

                            } else {
                                waterLevelHistoryInterface.onErrorGetWaterTankHistory("ERROR");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        waterLevelHistoryInterface.onErrorGetWaterTankHistory("ERROR");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {
                    waterLevelHistoryInterface.onErrorNoConnection();

                } else {
                    waterLevelHistoryInterface.onErrorNoConnection();
                }
            }
        }, waterTankId);
    }

    private long convertDateToTimeStampSeconds(String time) {
        Calendar calendar = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {
            Date date = sdf.parse(time);

            calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.HOUR_OF_DAY, 5);
            calendar.add(Calendar.MINUTE, 45);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
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