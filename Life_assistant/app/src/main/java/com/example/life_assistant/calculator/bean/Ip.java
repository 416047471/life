package com.example.life_assistant.calculator.bean;

public class Ip {
    private String Country;//国家/地区
    private String Province;//	省份区域，部分可能为空
    private String City;	//城市，部分可能为空
    private String Isp;//	城市，部分可能为空

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getIsp() {
        return Isp;
    }

    public void setIsp(String isp) {
        Isp = isp;
    }
}
