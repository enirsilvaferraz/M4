package com.system.m4.labs.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by eferraz on 10/06/17.
 * For M4
 */

public class SimpleItemListVO implements VOInterface {

    public static final Creator<SimpleItemListVO> CREATOR = new Creator<SimpleItemListVO>() {
        @Override
        public SimpleItemListVO createFromParcel(Parcel source) {
            return new SimpleItemListVO(source);
        }

        @Override
        public SimpleItemListVO[] newArray(int size) {
            return new SimpleItemListVO[size];
        }
    };

    private String key;
    private String name;

    public SimpleItemListVO(String key, String name) {
        this.key = key;
        this.name = name;
    }

    protected SimpleItemListVO(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
