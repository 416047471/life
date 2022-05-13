package com.example.life_assistant.index.gps;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.life_assistant.login.User;

import cn.bmob.v3.BmobObject;

public class Sport extends BmobObject implements Parcelable {
    private User user;
    private String sportPace;//运动配速
    private String sportDistance;//运动公里数
    private String sportTime;//运动时间
    private String sportHeat;//消耗大卡

    Sport(){super();}

    public Sport(User user, String sportPace, String sportDistance, String sportTime, String sportHeat) {
        this.user = user;
        this.sportPace = sportPace;
        this.sportDistance = sportDistance;
        this.sportTime = sportTime;
        this.sportHeat = sportHeat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSportPace() {
        return sportPace;
    }

    public void setSportPace(String sportPace) {
        this.sportPace = sportPace;
    }

    public String getSportDistance() {
        return sportDistance;
    }

    public void setSportDistance(String sportDistance) {
        this.sportDistance = sportDistance;
    }

    public String getSportTime() {
        return sportTime;
    }

    public void setSportTime(String sportTime) {
        this.sportTime = sportTime;
    }

    public String getSportHeat() {
        return sportHeat;
    }

    public void setSportHeat(String sportHeat) {
        this.sportHeat = sportHeat;
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

    protected Sport(Parcel in) {
        user = (User) in.readSerializable();
        sportPace = in.readString();
        sportDistance = in.readString();
        sportTime = in.readString();
        sportHeat = in.readString();
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
        //序列化对象，必须按照声明顺序
        dest.writeSerializable(user);
        dest.writeString(sportPace);
        dest.writeString(sportDistance);
        dest.writeString(sportTime);
        dest.writeString(sportHeat);
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        /**
         * 从Parcel中读取数据
         */
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };
}
