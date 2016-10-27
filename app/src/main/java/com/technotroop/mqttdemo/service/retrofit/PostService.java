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
    @POST("users")
    Call<ResponseBody> registerUser(
            @Field("email") String userEmail,
            @Field("firstname") String userFirstName,
            @Field("lastname") String userLastName,
            @Field("phone_number") String userPhoneNo,
            @Field("address") String address,
            @Field("city_id") String cityId,
            @Field("is_active") String isUserActive,
            @Field("water_pump_controller_id") String userIOTDeviceId);
}
