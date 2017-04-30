package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;

import com.system.m4.businness.dtos.TransactionDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TransactionVO implements Serializable, Parcelable {

    public static final Parcelable.Creator<TransactionVO> CREATOR = new Parcelable.Creator<TransactionVO>() {
        @Override
        public TransactionVO createFromParcel(Parcel source) {
            return new TransactionVO(source);
        }

        @Override
        public TransactionVO[] newArray(int size) {
            return new TransactionVO[size];
        }
    };

    private String paymentDate;
    private String purchaseDate;
    private String value;
    private String tags;
    private String paymentType;
    private String content;

    public TransactionVO(String paymentDate, String purchaseDate, String value, String tags, String paymentType, String content) {
        this.paymentDate = paymentDate;
        this.purchaseDate = purchaseDate;
        this.value = value;
        this.tags = tags;
        this.paymentType = paymentType;
        this.content = content;
    }

    public TransactionVO(Parcel in) {
        this.paymentDate = in.readString();
        this.purchaseDate = in.readString();
        this.value = in.readString();
        this.tags = in.readString();
        this.paymentType = in.readString();
        this.content = in.readString();
    }

    public TransactionVO(String tag) {
        this.tags = tag;
    }

    public static List<TransactionVO> asList(List<TransactionDTO> list) {
        List<TransactionVO> voList = new ArrayList<>();
        for (TransactionDTO dto : list) {
            voList.add(new TransactionVO(dto.getPaymentDate(), dto.getPurchaseDate(), dto.getPrice(), dto.getTag(), dto.getPaymentType(), dto.getContent()));
        }
        return voList;
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
