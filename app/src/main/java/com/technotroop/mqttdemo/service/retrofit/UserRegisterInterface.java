package com.technotroop.mqttdemo.service.retrofit;

import com.technotroop.mqttdemo.view.interfaces.ConnectionError;

/**
 * Created by technotroop on 10/18/16.
 */
public interface UserRegisterInterface  extends ConnectionError {

    void onSuccessUserRegister();

    void onErrorUserRegister();
}
