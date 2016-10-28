package com.technotroop.mqttdemo.service.data;

import com.technotroop.mqttdemo.service.model.WaterLevel;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.technotroop.mqttdemo.service.data.DataService.getRealm;

/**
 * Created by technotroop on 10/28/16.
 */

public class WaterLevelDataService {

    public static void saveWaterLevel(WaterLevel waterLevel) {

        Realm realm = getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(waterLevel);
        realm.commitTransaction();
        realm.close();
    }

    public static RealmResults<WaterLevel> getWaterLevel() {
        Realm realm = getRealm();
        RealmResults<WaterLevel> waterLevel = realm.where(WaterLevel.class).findAll();
        return waterLevel;
    }
}
