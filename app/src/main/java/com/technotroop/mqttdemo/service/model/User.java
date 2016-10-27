package com.technotroop.mqttdemo.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by technotroop on 10/18/16.
 */
public class User extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;

    private String email;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("phone_number")
    private String phoneNumber;
    private String address;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("landline")
    private String landLine;
    //1 means active, 0 means has not been inactive
    @SerializedName("is_active")
    private int isActive;
    @SerializedName("water_pump_controller_id")
    private String waterPumpControllerId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getWaterPumpControllerId() {
        return waterPumpControllerId;
    }

    public void setWaterPumpControllerId(String waterPumpControllerId) {
        this.waterPumpControllerId = waterPumpControllerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLandLine() {
        return landLine;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }
}
