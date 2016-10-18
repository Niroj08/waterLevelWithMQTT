package com.technotroop.mqttdemo;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by technotroop on 10/18/16.
 */
public class UserRegisterController {
    private UserRegisterService userRegisterService;

    public UserValidation isDataValidate(User user) {

        if (TextUtils.isEmpty(user.getEmail())) {
            return UserValidation.REQUIRED_EMAIL;
        } else if (TextUtils.isEmpty(user.getFirstName())) {
            return UserValidation.REQUIRED_FIRSTNAME;
        } else if (TextUtils.isEmpty(user.getLastName())) {
            return UserValidation.REQUIRED_LASTNAME;
        } else if (TextUtils.isEmpty(user.getPhoneNumber())) {
            return UserValidation.REQUIRED_PHONE;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            return UserValidation.INVALID_EMAIL;
        } else if (user.getPhoneNumber().length() != 10) {
            return UserValidation.INVALID_PHONE;
        }
        return UserValidation.TRUE;
    }

    public void registerUser(User user) {
        userRegisterService = new UserRegisterService();

        userRegisterService.userRegister(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                User userResponse = null;
                try {
                    ResponseBody responseBody = response.body();
                    ResponseBody errorBody = response.errorBody();
                    if (responseBody != null && errorBody == null) {

                    } else {
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {
                } else {
                }
            }
        }, user);
    }
}
