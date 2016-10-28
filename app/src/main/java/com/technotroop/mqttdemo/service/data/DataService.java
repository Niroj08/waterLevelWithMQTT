package com.technotroop.mqttdemo.service.data;

/**
 * Created by technotroop on 10/28/16.
 */

import com.technotroop.mqttdemo.utils.MQTTUtils;

import io.realm.Realm;

/**
 * Created by h on 7/6/16.
 */
public class DataService {

    public static Realm getRealm() {

        Realm.init(MQTTUtils.getContext());
        return Realm.getDefaultInstance();
    }
}
