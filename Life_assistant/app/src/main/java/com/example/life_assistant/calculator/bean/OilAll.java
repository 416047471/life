package com.example.life_assistant.calculator.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OilAll {
    private String reason;
    @SerializedName("result")
    private List<Oil> oil;
    private Integer error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Oil> getOil() {
        return oil;
    }

    public void setOil(List<Oil> oil) {
        this.oil = oil;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }
}
