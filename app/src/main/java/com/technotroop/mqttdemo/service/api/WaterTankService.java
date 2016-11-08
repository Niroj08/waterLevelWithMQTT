package com.technotroop.mqttdemo.service.api;

import com.technotroop.mqttdemo.service.retrofit.GetService;
import com.technotroop.mqttdemo.service.retrofit.RetrofitConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by technotroop on 10/27/16.
 */
public class WaterTankService {
    public void getWaterTanks(final Callback<ResponseBody> responseBodyCallback, String userId) {
        GetService getService =
                RetrofitConfig.createService(GetService.class);

        // finally, execute the request
        Call<ResponseBody> call = getService.getWaterTanks(userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                responseBodyCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseBodyCallback.onFailure(call, t);
            }
        });
    }
}
