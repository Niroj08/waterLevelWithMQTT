package com.technotroop.mqttdemo.service;

/**
 * Created by technotroop on 10/18/16.
 */

import com.technotroop.mqttdemo.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by technotroop on 6/23/16.
 */

public class RetrofitConfig {

    // enter the url of the API
    public static final String API_BASE_URL = Constants.serverBaseURL;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .retryOnConnectionFailure(false);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}

