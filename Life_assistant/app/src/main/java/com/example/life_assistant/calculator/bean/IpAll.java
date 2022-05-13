package com.example.life_assistant.calculator.bean;

import com.google.gson.annotations.SerializedName;

public class IpAll {
    private Integer resultcode;
    private String reason;

    @SerializedName("result")
    private Ip ip;

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

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }
}
