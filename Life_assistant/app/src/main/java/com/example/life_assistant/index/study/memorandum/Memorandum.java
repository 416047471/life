package com.example.life_assistant.index.study.memorandum;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.life_assistant.login.User;

import cn.bmob.v3.BmobObject;

public class Memorandum extends BmobObject implements Parcelable {
    private User user;
    private String memorandumTitle;
    private String memorandumContent;

    public Memorandum() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMemorandumTitle() {
        return memorandumTitle;
    }

    public void setMemorandumTitle(String memorandumTitle) {
        this.memorandumTitle = memorandumTitle;
    }

    public String getMemorandumContent() {
        return memorandumContent;
    }

    public void setMemorandumContent(String memorandumContent) {
        this.memorandumContent = memorandumContent;
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
    public Memorandum(Parcel parcel){
        user = (User) parcel.readSerializable();
        memorandumTitle = parcel.readString();
        memorandumContent = parcel.readString();
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
        dest.writeString(memorandumTitle);
        dest.writeString(memorandumContent);
    }
    public static final Creator<Memorandum> CREATOR = new Creator<Memorandum>() {
        /**
         * 从Parcel中读取数据
         */
        @Override
        public Memorandum createFromParcel(Parcel source) {
            return new Memorandum(source);
        }
        /**
         * 供外部类反序列化本类数组使用
         */
        @Override
        public Memorandum[] newArray(int size) {
            return new Memorandum[size];
        }
    };
}
