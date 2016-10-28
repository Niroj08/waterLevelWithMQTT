package com.technotroop.mqttdemo.service.api;

import com.technotroop.mqttdemo.service.retrofit.GetService;
import com.technotroop.mqttdemo.service.retrofit.PostService;
import com.technotroop.mqttdemo.service.retrofit.RetrofitConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by technotroop on 10/28/16.
 */
public class WaterLevelHistoryService {
    public void waterTankHistory(final Callback<ResponseBody> responseBodyCallback, String waterTankId) {
        // create upload service client
        PostService postService =
                RetrofitConfig.createService(PostService.class);

        // finally, execute the request
        Call<ResponseBody> call = postService.getWaterTankHistory(waterTankId);

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
