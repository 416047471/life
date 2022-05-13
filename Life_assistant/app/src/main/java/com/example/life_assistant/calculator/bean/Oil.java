package com.example.life_assistant.calculator.bean;

import com.google.gson.annotations.SerializedName;

public class Oil {
    private String city;
    @SerializedName("92h")
    private float ninety_two_liters;
    @SerializedName("95h")
    private float ninety_five_liters;
    @SerializedName("98h")
    private float ninety_eight_liters;
    @SerializedName("0h")
    private float zero_liters;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getNinety_two_liters() {
        return ninety_two_liters;
    }

    public void setNinety_two_liters(float ninety_two_liters) {
        this.ninety_two_liters = ninety_two_liters;
    }

    public float getNinety_five_liters() {
        return ninety_five_liters;
    }

    public void setNinety_five_liters(float ninety_five_liters) {
        this.ninety_five_liters = ninety_five_liters;
    }

    public float getNinety_eight_liters() {
        return ninety_eight_liters;
    }

    public void setNinety_eight_liters(float ninety_eight_liters) {
        this.ninety_eight_liters = ninety_eight_liters;
    }

    public float getZero_liters() {
        return zero_liters;
    }

    public void setZero_liters(float zero_liters) {
        this.zero_liters = zero_liters;
    }
}
