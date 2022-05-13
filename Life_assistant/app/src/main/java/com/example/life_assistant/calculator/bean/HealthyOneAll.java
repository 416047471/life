package com.example.life_assistant.calculator.bean;

import com.google.gson.annotations.SerializedName;

public class HealthyOneAll {
    private String reason;
    @SerializedName("result")
    private HealthyOne healthyOne;
    private Integer error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HealthyOne getHealthyOne() {
        return healthyOne;
    }

    public void setHealthyOne(HealthyOne healthyOne) {
        this.healthyOne = healthyOne;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }
}
