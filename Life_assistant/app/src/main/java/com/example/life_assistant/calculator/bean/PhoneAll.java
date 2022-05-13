package com.example.life_assistant.calculator.bean;

import com.google.gson.annotations.SerializedName;

public class PhoneAll {
    private Integer resultcode;
    private String reason;

    @SerializedName("result")
    private Phone phone;

    public Integer getResultcode() {
        return resultcode;
    }

    public void setResultcode(Integer resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
