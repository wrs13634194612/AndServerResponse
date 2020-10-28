package com.example.serverdemo.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
public class UserInfo implements Parcelable {

    @JSONField(name = "userId")
    private String mUserId;
    @JSONField(name = "userName")
    private String mUserName;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        mUserId = in.readString();
        mUserName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserId);
        dest.writeString(mUserName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mUserId='" + mUserId + '\'' +
                ", mUserName='" + mUserName + '\'' +
                '}';
    }
}