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
     * 这里的读的顺序必须与writeToParcel(Parcel dest, int flags)方法中
     * 写的顺序一致，否则数据会有差错，比如你的读取顺序如果是：
     * nickname = source.readString();
     * username=source.readString();
     * age = source.readInt();
     * 即调换了username和nickname的读取顺序，那么你会发现你拿到的username是nickname的数据，
     * 而你拿到的nickname是username的数据
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
     * 内容描述接口
     * 这里默认返回0即可
     */
    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * 写入接口函数，打包
     * 把值写入Parcel中
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //序列表对象，必须按照声明顺序
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
         * 从Parcel中读取数据
         */
        @Override
        public Target createFromParcel(Parcel source) {
            return new Target(source);
        }
        /**
         * 供外部类反序列化本类数组使用
         */
        @Override
        public Target[] newArray(int size) {
            return new Target[size];
        }
    };
}
