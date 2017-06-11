package com.system.m4.views.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 *
 */
public class FilterTransactionVO implements VOInterface<FilterTransactionVO> {

    public static final Creator<FilterTransactionVO> CREATOR = new Creator<FilterTransactionVO>() {
        @Override
        public FilterTransactionVO createFromParcel(Parcel source) {
            return new FilterTransactionVO(source);
        }

        @Override
        public FilterTransactionVO[] newArray(int size) {
            return new FilterTransactionVO[size];
        }
    };
    private String key;
    private Integer year;
    private Integer month;
    private TagVO tag;
    private PaymentTypeVO paymentType;

    public FilterTransactionVO() {
    }

    protected FilterTransactionVO(Parcel in) {
        this.key = in.readString();
        this.year = (Integer) in.readValue(Integer.class.getClassLoader());
        this.month = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tag = in.readParcelable(TagVO.class.getClassLoader());
        this.paymentType = in.readParcelable(PaymentTypeVO.class.getClassLoader());
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public TagVO getTag() {
        return tag;
    }

    public void setTag(TagVO tag) {
        this.tag = tag;
    }

    public PaymentTypeVO getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeVO paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int compareTo(@NonNull FilterTransactionVO o) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeValue(this.year);
        dest.writeValue(this.month);
        dest.writeParcelable(this.tag, flags);
        dest.writeParcelable(this.paymentType, flags);
    }
}
