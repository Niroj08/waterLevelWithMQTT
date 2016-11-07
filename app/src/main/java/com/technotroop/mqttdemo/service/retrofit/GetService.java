package com.technotroop.mqttdemo.service.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by technotroop on 10/27/16.
 */

public interface GetService {

    @GET("city")
    Call<ResponseBody> getCities();

    @GET("watertank")
    Call<ResponseBody> getWaterTanks();

    @GET("waterlevel/daily/{id}")
    Call<ResponseBody> getWaterTankHistory(@Path("id") String deviceId);
}
