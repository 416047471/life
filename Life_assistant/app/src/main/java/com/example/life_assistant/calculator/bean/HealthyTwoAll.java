package com.example.life_assistant.calculator.bean;

import com.google.gson.annotations.SerializedName;

public class HealthyTwoAll {
    private String reason;
    @SerializedName("result")
    private HealthyTwo healthyTwo;
    private Integer error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HealthyTwo getHealthyTwo() {
        return healthyTwo;
    }

    public void setHealthyTwo(HealthyTwo healthyTwo) {
        this.healthyTwo = healthyTwo;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }
}
