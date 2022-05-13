package com.example.life_assistant.calculator.bean;

public class Phone {
    private String province;    //省份
    private String city;        //城市，(北京、上海、重庆、天津直辖市可能为空)
    private String areacode;    //区号，(部分记录可能为空)
    private String zip;         //邮编，(部分记录可能为空)
    private String company;     //运营商

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
