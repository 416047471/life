package com.example.life_assistant.index.study.diary;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.life_assistant.login.User;

import cn.bmob.v3.BmobObject;

public class Diary extends BmobObject implements Parcelable {
    private User user;
    private String diaryTitle;//日记标题
    private String diaryContent;//日记内容

    public Diary(){super();}
    public Diary(User user, String diaryTitle, String diaryContent) {
        this.user = user;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
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


    protected Diary(Parcel parcel) {
        user = (User) parcel.readSerializable();
        diaryTitle = parcel.readString();
        diaryContent = parcel.readString();
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
        dest.writeString(diaryTitle);
        dest.writeString(diaryContent);
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        /**
         * 从Parcel中读取数据
         */
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };
}
