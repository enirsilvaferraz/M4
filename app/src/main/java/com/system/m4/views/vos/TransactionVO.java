package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;

import com.system.m4.repository.dtos.TransactionDTO;

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
    private String price;
    private String tag;
    private String paymentType;
    private String content;

    public TransactionVO() {
    }

    public TransactionVO(Parcel in) {
        this.paymentDate = in.readString();
        this.purchaseDate = in.readString();
        this.price = in.readString();
        this.tag = in.readString();
        this.paymentType = in.readString();
        this.content = in.readString();
    }

    public TransactionVO(TransactionDTO dto) {
        this.paymentDate = dto.getPaymentDate();
        this.purchaseDate = dto.getPurchaseDate();
        this.price = dto.getPrice();
        this.tag = dto.getTag().getName();
        this.paymentType = dto.getPaymentType();
        this.content = dto.getContent();
    }

    public static List<TransactionVO> asList(List<TransactionDTO> list) {
        List<TransactionVO> voList = new ArrayList<>();
        for (TransactionDTO dto : list) {
            voList.add(new TransactionVO(dto));
        }
        return voList;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentDate);
        dest.writeString(this.purchaseDate);
        dest.writeString(this.price);
        dest.writeString(this.tag);
        dest.writeString(this.paymentType);
        dest.writeString(this.content);
    }
}
