package com.system.m4.labs.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class PaymentTypeVO implements VOInterface<PaymentTypeVO> {

    public static final Creator<PaymentTypeVO> CREATOR = new Creator<PaymentTypeVO>() {
        @Override
        public PaymentTypeVO createFromParcel(Parcel source) {
            return new PaymentTypeVO(source);
        }

        @Override
        public PaymentTypeVO[] newArray(int size) {
            return new PaymentTypeVO[size];
        }
    };

    private String key;
    private String name;
    private String color;

    public PaymentTypeVO(String key, String name, String color) {
        this.key = key;
        this.name = name;
        this.color = color;
    }

    public PaymentTypeVO() {
    }

    public PaymentTypeVO(String key) {
        this.key = key;
    }

    protected PaymentTypeVO(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
    public int compareTo(@NonNull PaymentTypeVO o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentTypeVO typeVO = (PaymentTypeVO) o;

        return key != null ? key.equals(typeVO.key) : typeVO.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
