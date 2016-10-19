package com.technotroop.mqttdemo.service.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by technotroop on 10/18/16.
 */

public interface PostService {

    @FormUrlEncoded
    @POST("api/user_register")
    Call<ResponseBody> registerUser(
            @Field("userEmail") String userEmail,
            @Field("userFirstName") String userFirstName,
            @Field("userLastName") String userLastName,
            @Field("userPhoneNo") String userPhoneNo,
            @Field("userMobileDeviceId") String userMobileDeviceId,
            @Field("userIOTDeviceId") String userIOTDeviceId);
}
