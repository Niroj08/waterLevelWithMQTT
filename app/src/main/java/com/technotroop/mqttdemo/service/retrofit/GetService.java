package com.technotroop.mqttdemo.service.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by technotroop on 10/27/16.
 */

public interface GetService {

    @GET("city")
    Call<ResponseBody> getCities();

    @GET("watertank")
    Call<ResponseBody> getWaterTanks();
}
