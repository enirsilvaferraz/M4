package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class FilterTransactionVO implements Serializable, Parcelable {

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

    private Date paymentDateStart;
    private Date paymentDateEnd;
    private String tags;
    private String paymentType;

    public FilterTransactionVO(Date paymentDateStart, Date paymentDateEnd, String tags, String paymentType) {
        this.paymentDateStart = paymentDateStart;
        this.paymentDateEnd = paymentDateEnd;
        this.tags = tags;
        this.paymentType = paymentType;
    }

    public FilterTransactionVO() {

    }

    protected FilterTransactionVO(Parcel in) {
        long tmpPaymentDateStart = in.readLong();
        this.paymentDateStart = tmpPaymentDateStart == -1 ? null : new Date(tmpPaymentDateStart);
        long tmpPaymentDateEnd = in.readLong();
        this.paymentDateEnd = tmpPaymentDateEnd == -1 ? null : new Date(tmpPaymentDateEnd);
        this.tags = in.readString();
        this.paymentType = in.readString();
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.paymentDateStart != null ? this.paymentDateStart.getTime() : -1);
        dest.writeLong(this.paymentDateEnd != null ? this.paymentDateEnd.getTime() : -1);
        dest.writeString(this.tags);
        dest.writeString(this.paymentType);
    }
}
