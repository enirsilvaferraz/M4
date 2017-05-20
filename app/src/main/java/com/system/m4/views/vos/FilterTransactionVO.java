package com.system.m4.views.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Date;

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
    private Date paymentDateStart;
    private Date paymentDateEnd;
    private TagVO tag;
    private PaymentTypeVO paymentType;

    public FilterTransactionVO() {

    }

    private FilterTransactionVO(Parcel in) {
        this.key = in.readString();
        long tmpPaymentDateStart = in.readLong();
        this.paymentDateStart = tmpPaymentDateStart == -1 ? null : new Date(tmpPaymentDateStart);
        long tmpPaymentDateEnd = in.readLong();
        this.paymentDateEnd = tmpPaymentDateEnd == -1 ? null : new Date(tmpPaymentDateEnd);
        this.tag = in.readParcelable(TagVO.class.getClassLoader());
        this.paymentType = in.readParcelable(PaymentTypeVO.class.getClassLoader());
    }

    public Date getPaymentDateStart() {
        return paymentDateStart;
    }

    public void setPaymentDateStart(Date paymentDateStart) {
        this.paymentDateStart = paymentDateStart;
    }

    public Date getPaymentDateEnd() {
        return paymentDateEnd;
    }

    public void setPaymentDateEnd(Date paymentDateEnd) {
        this.paymentDateEnd = paymentDateEnd;
    }

    public TagVO getTag() {
        return tag;
    }

    public void setTag(TagVO tags) {
        this.tag = tags;
    }

    public PaymentTypeVO getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeVO paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(@NonNull FilterTransactionVO vo) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeLong(this.paymentDateStart != null ? this.paymentDateStart.getTime() : -1);
        dest.writeLong(this.paymentDateEnd != null ? this.paymentDateEnd.getTime() : -1);
        dest.writeParcelable(this.tag, flags);
        dest.writeParcelable(this.paymentType, flags);
    }
}
