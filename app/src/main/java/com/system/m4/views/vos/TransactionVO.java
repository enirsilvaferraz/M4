package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 *
 */
public class TransactionVO implements Parcelable, Comparable<TransactionVO> {

    public static final Creator<TransactionVO> CREATOR = new Creator<TransactionVO>() {
        @Override
        public TransactionVO createFromParcel(Parcel source) {
            return new TransactionVO(source);
        }

        @Override
        public TransactionVO[] newArray(int size) {
            return new TransactionVO[size];
        }
    };

    private String key;
    private Date paymentDate;
    private Date purchaseDate;
    private String price;
    private TagVO tag;
    private PaymentTypeVO paymentType;
    private String content;

    public TransactionVO() {
    }

    public TransactionVO(TagVO tagVO) {
        this.tag = tagVO;
    }

    protected TransactionVO(Parcel in) {
        this.key = in.readString();
        long tmpPaymentDate = in.readLong();
        this.paymentDate = tmpPaymentDate == -1 ? null : new Date(tmpPaymentDate);
        long tmpPurchaseDate = in.readLong();
        this.purchaseDate = tmpPurchaseDate == -1 ? null : new Date(tmpPurchaseDate);
        this.price = in.readString();
        this.tag = in.readParcelable(TagVO.class.getClassLoader());
        this.paymentType = in.readParcelable(PaymentTypeVO.class.getClassLoader());
        this.content = in.readString();
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeLong(this.paymentDate != null ? this.paymentDate.getTime() : -1);
        dest.writeLong(this.purchaseDate != null ? this.purchaseDate.getTime() : -1);
        dest.writeString(this.price);
        dest.writeParcelable(this.tag, flags);
        dest.writeParcelable(this.paymentType, flags);
        dest.writeString(this.content);
    }

    @Override
    public int compareTo(@NonNull TransactionVO o) {
        return paymentDate.compareTo(o.getPaymentDate());
    }
}
