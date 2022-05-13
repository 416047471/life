package com.example.life_assistant.login;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/*
* 用户信息实体类
* */
public class User extends BmobObject implements Serializable{
    private String userName;//用户的用户名
    private String userPassword;//用户密码
    private String mobilePhoneNumber;//用户手机号
    private String email;//用户邮箱
    private Integer userAge;//用户的年龄
    private String userGender;//用户性别
    private Integer userBalance;//用户余额

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Integer getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Integer userBalance) {
        this.userBalance = userBalance;
    }
}
