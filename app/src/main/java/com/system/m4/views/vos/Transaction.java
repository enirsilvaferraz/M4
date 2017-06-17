package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 *
 */
public class Transaction implements VOInterface<Transaction>, VOItemListInterface, Parcelable, Comparable<Transaction> {

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
    private String key;
    private Date paymentDate;
    private Date paymentDateOrigin;
    private Date purchaseDate;
    private Double price;
    private TagVO tag;
    private PaymentTypeVO paymentType;
    private String content;
    private boolean pinned;
    private boolean clickable = true;
    private boolean approved = true;

    public Transaction() {
        // Default constructor
    }

    public Transaction(TagVO tagVO) {
        this.tag = tagVO;
    }

    protected Transaction(Parcel in) {
        this.key = in.readString();
        long tmpPaymentDate = in.readLong();
        this.paymentDate = tmpPaymentDate == -1 ? null : new Date(tmpPaymentDate);
        long tmpPaymentDateOrigin = in.readLong();
        this.paymentDateOrigin = tmpPaymentDateOrigin == -1 ? null : new Date(tmpPaymentDateOrigin);
        long tmpPurchaseDate = in.readLong();
        this.purchaseDate = tmpPurchaseDate == -1 ? null : new Date(tmpPurchaseDate);
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.tag = in.readParcelable(TagVO.class.getClassLoader());
        this.paymentType = in.readParcelable(PaymentTypeVO.class.getClassLoader());
        this.content = in.readString();
        this.pinned = in.readByte() != 0;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return key != null ? key.equals(that.key) : that.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
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

    @Override
    public int compareTo(@NonNull Transaction o) {

        int compareTo = paymentDate.compareTo(o.getPaymentDate());
        if (compareTo != 0) {
            return compareTo;
        }

        compareTo = paymentType.compareTo(o.paymentType);
        if (compareTo != 0) {
            return compareTo;
        }

        return tag.compareTo(o.tag);
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Date getPaymentDateOrigin() {
        return paymentDateOrigin;
    }

    public void setPaymentDateOrigin(Date paymentDateOrigin) {
        this.paymentDateOrigin = paymentDateOrigin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeLong(this.paymentDate != null ? this.paymentDate.getTime() : -1);
        dest.writeLong(this.paymentDateOrigin != null ? this.paymentDateOrigin.getTime() : -1);
        dest.writeLong(this.purchaseDate != null ? this.purchaseDate.getTime() : -1);
        dest.writeValue(this.price);
        dest.writeParcelable(this.tag, flags);
        dest.writeParcelable(this.paymentType, flags);
        dest.writeString(this.content);
        dest.writeByte(this.pinned ? (byte) 1 : (byte) 0);
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }
}
