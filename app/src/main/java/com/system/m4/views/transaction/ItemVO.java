package com.system.m4.views.transaction;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 *
 */
public class ItemVO implements Serializable, Parcelable {

    public static final Parcelable.Creator<ItemVO> CREATOR = new Parcelable.Creator<ItemVO>() {
        @Override
        public ItemVO createFromParcel(Parcel source) {
            return new ItemVO(source);
        }

        @Override
        public ItemVO[] newArray(int size) {
            return new ItemVO[size];
        }
    };

    private String paymentDate;
    private String purchaseDate;
    private String value;
    private String tags;
    private String paymentType;
    private String content;

    public ItemVO(String paymentDate, String purchaseDate, String value, String tags, String paymentType, String content) {
        this.paymentDate = paymentDate;
        this.purchaseDate = purchaseDate;
        this.value = value;
        this.tags = tags;
        this.paymentType = paymentType;
        this.content = content;
    }

    public ItemVO(Parcel in) {
        this.paymentDate = in.readString();
        this.purchaseDate = in.readString();
        this.value = in.readString();
        this.tags = in.readString();
        this.paymentType = in.readString();
        this.content = in.readString();
    }

    public ItemVO(String tag) {
        this.tags = tag;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getValue() {
        return value;
    }

    public String getTags() {
        return tags;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentDate);
        dest.writeString(this.purchaseDate);
        dest.writeString(this.value);
        dest.writeString(this.tags);
        dest.writeString(this.paymentType);
        dest.writeString(this.content);
    }
}
