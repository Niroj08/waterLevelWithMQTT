package com.technotroop.mqttdemo.service.api;

import com.technotroop.mqttdemo.service.model.User;
import com.technotroop.mqttdemo.service.retrofit.GetService;
import com.technotroop.mqttdemo.service.retrofit.PostService;
import com.technotroop.mqttdemo.service.retrofit.RetrofitConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by technotroop on 10/18/16.
 */
public class UserRegisterService {

    public void userRegister(final Callback<ResponseBody> responseBodyCallback, User user) {
        // create upload service client
        PostService userRegisterService =
                RetrofitConfig.createService(PostService.class);

        // finally, execute the request
        Call<ResponseBody> call = userRegisterService.registerUser(user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(), user.getAddress(), user.getCityId(), String.valueOf(user.getIsActive()), user.getWaterPumpControllerId());

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

    public void getCities(final Callback<ResponseBody> responseBodyCallback) {
        // create upload service client
        GetService getService =
                RetrofitConfig.createService(GetService.class);

        // finally, execute the request
        Call<ResponseBody> call = getService.getCities();

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
