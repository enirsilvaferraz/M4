package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;

import com.system.m4.repository.dtos.FilterTransactionDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private String paymentDateStart;
    private String paymentDateEnd;
    private String tags;
    private String paymentType;

    public FilterTransactionVO(String paymentDateStart, String paymentDateEnd, String tags, String paymentType) {
        this.paymentDateStart = paymentDateStart;
        this.paymentDateEnd = paymentDateEnd;
        this.tags = tags;
        this.paymentType = paymentType;
    }

    protected FilterTransactionVO(Parcel in) {
        this.paymentDateStart = in.readString();
        this.paymentDateEnd = in.readString();
        this.tags = in.readString();
        this.paymentType = in.readString();
    }

    public FilterTransactionVO() {

    }

    public static List<FilterTransactionVO> asList(List<FilterTransactionDTO> list) {
        List<FilterTransactionVO> voList = new ArrayList<>();
        for (FilterTransactionDTO dto : list) {
            voList.add(new FilterTransactionVO(dto.getPaymentDateStart(), dto.getPaymentDateEnd(), dto.getTags(), dto.getPaymentType()));
        }
        return voList;
    }

    public String getPaymentDateStart() {

        return paymentDateStart;
    }

    public void setPaymentDateStart(String paymentDateStart) {
        this.paymentDateStart = paymentDateStart;
    }

    public String getPaymentDateEnd() {
        return paymentDateEnd;
    }

    public void setPaymentDateEnd(String paymentDateEnd) {
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
        dest.writeString(this.paymentDateStart);
        dest.writeString(this.paymentDateEnd);
        dest.writeString(this.tags);
        dest.writeString(this.paymentType);
    }
}
