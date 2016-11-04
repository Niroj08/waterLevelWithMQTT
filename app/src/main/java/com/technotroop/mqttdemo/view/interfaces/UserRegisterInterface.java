package com.technotroop.mqttdemo.view.interfaces;

import com.technotroop.mqttdemo.service.model.City;
import com.technotroop.mqttdemo.service.model.User;

import java.util.ArrayList;

/**
 * Created by technotroop on 10/18/16.
 */
public interface UserRegisterInterface extends ConnectionError {

    void onSuccessUserRegister(User user);

    void onErrorUserRegister(String error);

    void onSuccessGetCities(ArrayList<City> cityList);

    void onErrorGetCities(String error);

    void onLoginSuccess(User user);

    void onLoginError(String error);
}
