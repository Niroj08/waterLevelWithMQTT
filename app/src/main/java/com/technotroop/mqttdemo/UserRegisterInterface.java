package com.technotroop.mqttdemo;

/**
 * Created by technotroop on 10/18/16.
 */
public interface UserRegisterInterface  extends ConnectionError{

    void onSuccessUserRegister();

    void onErrorUserRegister();
}
