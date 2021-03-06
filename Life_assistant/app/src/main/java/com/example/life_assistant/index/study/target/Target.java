package com.example.life_assistant.index.study.target;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.life_assistant.login.User;

import cn.bmob.v3.BmobObject;

public class Target extends BmobObject implements Parcelable {
    private User user;
    private String targetTitle;
    private String targetContentOne;
    private Integer targetStateOne;
    private String targetContentTwo;
    private Integer targetStateTwo;
    private String targetContentThree;
    private Integer targetStateThree;

    public Target(){};

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public String getTargetContentOne() {
        return targetContentOne;
    }

    public void setTargetContentOne(String targetContentOne) {
        this.targetContentOne = targetContentOne;
    }

    public Integer getTargetStateOne() {
        return targetStateOne;
    }

    public void setTargetStateOne(Integer targetStateOne) {
        this.targetStateOne = targetStateOne;
    }

    public String getTargetContentTwo() {
        return targetContentTwo;
    }

    public void setTargetContentTwo(String targetContentTwo) {
        this.targetContentTwo = targetContentTwo;
    }

    public Integer getTargetStateTwo() {
        return targetStateTwo;
    }

    public void setTargetStateTwo(Integer targetStateTwo) {
        this.targetStateTwo = targetStateTwo;
    }

    public String getTargetContentThree() {
        return targetContentThree;
    }

    public void setTargetContentThree(String targetContentThree) {
        this.targetContentThree = targetContentThree;
    }

    public Integer getTargetStateThree() {
        return targetStateThree;
    }

    public void setTargetStateThree(Integer targetStateThree) {
        this.targetStateThree = targetStateThree;
    }

    /*
     * ??????????????????????????????writeToParcel(Parcel dest, int flags)?????????
     * ????????????????????????????????????????????????????????????????????????????????????
     * nickname = source.readString();
     * username=source.readString();
     * age = source.readInt();
     * ????????????username???nickname????????????????????????????????????????????????username???nickname????????????
     * ???????????????nickname???username?????????
     */
    public Target(Parcel parcel){
        user = (User) parcel.readSerializable();
        targetContentOne = parcel.readString();
        targetStateOne = parcel.readInt();
        targetContentTwo = parcel.readString();
        targetStateTwo = parcel.readInt();
        targetContentThree = parcel.readString();
        targetStateThree = parcel.readInt();
    }
    /**
     * ??????????????????
     * ??????????????????0??????
     */
    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * ???????????????????????????
     * ????????????Parcel???
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //??????????????????????????????????????????
        dest.writeSerializable(user);
        dest.writeString(targetContentOne);
        dest.writeInt(targetStateOne);
        dest.writeString(targetContentTwo);
        dest.writeInt(targetStateTwo);
        dest.writeString(targetContentThree);
        dest.writeInt(targetStateThree);
    }

    public static final Creator<Target> CREATOR = new Creator<Target>() {
        /**
         * ???Parcel???????????????
         */
        @Override
        public Target createFromParcel(Parcel source) {
            return new Target(source);
        }
        /**
         * ??????????????????????????????????????????
         */
        @Override
        public Target[] newArray(int size) {
            return new Target[size];
        }
    };
}
