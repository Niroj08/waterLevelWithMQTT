package com.technotroop.mqttdemo.controller;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.technotroop.mqttdemo.service.model.City;
import com.technotroop.mqttdemo.utils.Constants;
import com.technotroop.mqttdemo.view.interfaces.UserRegisterInterface;
import com.technotroop.mqttdemo.service.api.UserRegisterService;
import com.technotroop.mqttdemo.utils.enums.UserValidation;
import com.technotroop.mqttdemo.service.model.User;

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
 * Created by technotroop on 10/18/16.
 */
public class UserRegisterController {
    private UserRegisterService userRegisterService = new UserRegisterService();
    UserRegisterInterface userRegister;
    City city;
    ArrayList<City> cityList;

    public UserRegisterController(UserRegisterInterface userRegisterInterface) {
        userRegister = userRegisterInterface;
    }

    public UserValidation isDataValidate(User user) {

        if (TextUtils.isEmpty(user.getEmail())) {
            return UserValidation.REQUIRED_EMAIL;
        } else if (TextUtils.isEmpty(user.getFirstName())) {
            return UserValidation.REQUIRED_FIRSTNAME;
        } else if (TextUtils.isEmpty(user.getLastName())) {
            return UserValidation.REQUIRED_LASTNAME;
        } else if (TextUtils.isEmpty(user.getPhoneNumber())) {
            return UserValidation.REQUIRED_PHONE;
        } else if (TextUtils.isEmpty(user.getLandLine())) {
            return UserValidation.REQUIRED_LANDLINE;
        } else if (TextUtils.isEmpty(user.getAddress())) {
            return UserValidation.REQUIRED_ADDRESS;
        } else if (TextUtils.isEmpty(user.getWaterPumpControllerId())) {
            return UserValidation.REQUIRED_DEVICE_ID;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            return UserValidation.INVALID_EMAIL;
        } else if (user.getPhoneNumber().length() != 10) {
            return UserValidation.INVALID_PHONE;
        }
        return UserValidation.TRUE;
    }

    public void registerUser(User user) {

        userRegisterService.userRegister(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                User userResponse = null;
                try {
                    ResponseBody responseBody = response.body();
                    ResponseBody errorBody = response.errorBody();
                    if (responseBody != null && errorBody == null) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body().string());
                            if (responseObject.optString("status").equalsIgnoreCase("1")) {

                                JSONObject data = responseObject.optJSONObject("data");

                                Gson gson = new Gson();
                                User user = gson.fromJson(String.valueOf(data), User.class);

                                userRegister.onSuccessUserRegister(user);

                            } else {

                                //TODO: except status success
                                userRegister.onErrorUserRegister("Error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        userRegister.onErrorUserRegister("Error");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {
                    userRegister.onErrorNoConnection();
                } else {
                }
            }
        }, user);
    }

    public void getCities() {
        userRegisterService.getCities(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    ResponseBody responseBody = response.body();
                    ResponseBody errorBody = response.errorBody();
                    if (responseBody != null && errorBody == null) {
                        cityList = new ArrayList<City>();

                        try {
                            JSONObject responseObject = new JSONObject(response.body().string());
                            Gson gson = new Gson();
                            if (responseObject.optString("status").equalsIgnoreCase("1")) {
                                JSONArray cities = responseObject.getJSONArray("cities");
                                for (int i = 0; i < cities.length(); i++) {
                                    city = new City();
                                    JSONObject cityObject = cities.getJSONObject(i);
                                    city = gson.fromJson(String.valueOf(cityObject), City.class);

                                    cityList.add(city);
                                }

                                userRegister.onSuccessGetCities(cityList);
                            } else {
                                //TODO: except status success
                                userRegister.onErrorGetCities("Error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                        userRegister.onErrorGetCities("Error");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {
                    userRegister.onErrorNoConnection();
                } else {

                    userRegister.onErrorNoConnection();
                }
            }
        });
    }


    public void userLogin(String emailID, String deviceId) {
        userRegisterService.userLogin(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    ResponseBody responseBody = response.body();
                    ResponseBody errorBody = response.errorBody();
                    if (responseBody != null && errorBody == null) {

                        try {
                            JSONObject responseObject = new JSONObject(responseBody.string());
                            if (responseObject.optString("status").equalsIgnoreCase("1")) {
                                userRegister.onLoginSuccess();
                            } else {
                                userRegister.onLoginError("Error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        userRegister.onLoginError("Error");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getCause() instanceof SocketTimeoutException
                        || t.getMessage().equalsIgnoreCase("Failed to connect to " + Constants.serverBaseURL)) {
                    userRegister.onErrorNoConnection();
                } else {

                    userRegister.onErrorNoConnection();
                }
            }
        }, emailID, deviceId);
    }
}
