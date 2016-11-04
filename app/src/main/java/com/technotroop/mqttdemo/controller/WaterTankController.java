package com.technotroop.mqttdemo.controller;

import com.google.gson.Gson;
import com.technotroop.mqttdemo.service.api.WaterTankService;
import com.technotroop.mqttdemo.service.model.City;
import com.technotroop.mqttdemo.service.model.WaterTank;
import com.technotroop.mqttdemo.utils.Constants;
import com.technotroop.mqttdemo.utils.enums.ResponseStatus;
import com.technotroop.mqttdemo.view.interfaces.WaterTankInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by technotroop on 10/27/16.
 */
public class WaterTankController {

    private WaterTank waterTank;
    private ArrayList<WaterTank> waterTankList;

    private WaterTankInterface waterTankInterface;
    private WaterTankService waterTankService = new WaterTankService();

    public WaterTankController(WaterTankInterface waterTanInterface) {
        this.waterTankInterface = waterTanInterface;
    }

    public void getWaterTanks() {
        waterTankService.getWaterTanks(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    ResponseBody responseBody = response.body();
                    ResponseBody errorBody = response.errorBody();
                    if (responseBody != null && errorBody == null) {
                        waterTankList = new ArrayList<WaterTank>();

                        try {
                            JSONObject responseObject = new JSONObject(response.body().string());
                            Gson gson = new Gson();
                            if (responseObject.optString("status").equalsIgnoreCase(String.valueOf(ResponseStatus.SUCCESS))
                                    || responseObject.optString("status").equalsIgnoreCase(String.valueOf(ResponseStatus.SUCCESSS)) ) {

                                JSONArray data = responseObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    waterTank = new WaterTank();
                                    JSONObject cityObject = data.getJSONObject(i);
                                    waterTank = gson.fromJson(String.valueOf(cityObject), WaterTank.class);

                                    waterTankList.add(waterTank);
                                }

                                waterTankInterface.onSuccessGetWaterTank(waterTankList);
                            } else {
                                //TODO: except status success
                                waterTankInterface.onErrorGetWaterTank("ERROR");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                        waterTankInterface.onErrorGetWaterTank("Error");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {
                    waterTankInterface.onErrorNoConnection();
                } else {

                    waterTankInterface.onErrorNoConnection();
                }
            }
        });
    }
}
