package com.example.life_assistant.index.study.matter;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.life_assistant.login.User;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Matter extends BmobObject implements Parcelable {
    private User user;
    private String matterTitle;//目录名
    private String matterContent;//目录内容
    private Integer matterState;//目录状态

    public Matter(){super();}
    public Matter(User user,String matterTitle,String matterContent,Integer matterState){
        this.user = user;
        this.matterTitle = matterTitle;
        this.matterContent = matterContent;
        this.matterState = matterState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMatterTitle() {
        return matterTitle;
    }

    public void setMatterTitle(String matterTitle) {
        this.matterTitle = matterTitle;
    }

    public String getMatterContent() {
        return matterContent;
    }

    public void setMatterContent(String matterContent) {
        this.matterContent = matterContent;
    }

    public Integer getMatterState() {
        return matterState;
    }

    public void setMatterState(Integer matterState) {
        this.matterState = matterState;
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
    public Matter(Parcel parcel){
        user = (User) parcel.readSerializable();
        matterTitle = parcel.readString();
        matterContent = parcel.readString();
        matterState = parcel.readInt();
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
        dest.writeString(matterTitle);
        dest.writeString(matterContent);
        dest.writeInt(matterState);
    }

    public static final Creator<Matter> CREATOR = new Creator<Matter>() {
        /**
         * 从Parcel中读取数据
         */
        @Override
        public Matter createFromParcel(Parcel source) {
            return new Matter(source);
        }

        /**
         * 供外部类反序列化本类数组使用
         */
        @Override
        public Matter[] newArray(int size) {
            return new Matter[size];
        }
    };

}
