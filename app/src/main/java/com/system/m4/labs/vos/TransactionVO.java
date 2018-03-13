package com.system.m4.labs.vos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 *
 */
public class TransactionVO implements VOInterface<TransactionVO>, VOItemListInterface, Parcelable, Comparable<TransactionVO> {

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
    public boolean alreadyPaid = true;
    private String key;
    private Date paymentDate;
    private Date paymentDateOrigin;
    private Date purchaseDate;
    private Double price;
    private Double refund;
    private TagVO tag;
    private PaymentTypeVO paymentType;
    private String content;
    private boolean clickable = true;
    private boolean approved = true;
    private boolean onGroup;
    private String parcels;

    public TransactionVO() {
        // Default constructor
    }

    public TransactionVO(TransactionVO vo) {
        this.key = vo.key;
        this.paymentDate = vo.paymentDate;
        this.paymentDateOrigin = vo.paymentDateOrigin;
        this.purchaseDate = vo.purchaseDate;
        this.price = vo.price;
        this.refund = vo.refund;
        this.tag = vo.tag;
        this.paymentType = vo.paymentType;
        this.content = vo.content;
        this.clickable = vo.clickable;
        this.approved = vo.approved;
        this.onGroup = vo.onGroup;
        this.parcels = vo.parcels;
    }

    public TransactionVO(TagVO tagVO) {
        this.tag = tagVO;
    }

    protected TransactionVO(Parcel in) {
        this.key = in.readString();
        long tmpPaymentDate = in.readLong();
        this.paymentDate = tmpPaymentDate == -1 ? null : new Date(tmpPaymentDate);
        long tmpPaymentDateOrigin = in.readLong();
        this.paymentDateOrigin = tmpPaymentDateOrigin == -1 ? null : new Date(tmpPaymentDateOrigin);
        long tmpPurchaseDate = in.readLong();
        this.purchaseDate = tmpPurchaseDate == -1 ? null : new Date(tmpPurchaseDate);
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.refund = (Double) in.readValue(Double.class.getClassLoader());
        this.tag = in.readParcelable(TagVO.class.getClassLoader());
        this.paymentType = in.readParcelable(PaymentTypeVO.class.getClassLoader());
        this.content = in.readString();
        this.clickable = in.readByte() != 0;
        this.approved = in.readByte() != 0;
        this.onGroup = in.readByte() != 0;
        this.parcels = in.readString();
        this.alreadyPaid = in.readByte() != 0;
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

    public String getParcels() {
        return parcels;
    }

    public void setParcels(String parcels) {
        this.parcels = parcels;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionVO that = (TransactionVO) o;

        return key != null ? key.equals(that.key) : that.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(@NonNull TransactionVO o) {

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

    public Date getPaymentDateOrigin() {
        return paymentDateOrigin;
    }

    public void setPaymentDateOrigin(Date paymentDateOrigin) {
        this.paymentDateOrigin = paymentDateOrigin;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isOnGroup() {
        return onGroup;
    }

    public void setOnGroup(boolean onGroup) {
        this.onGroup = onGroup;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public Double getTotal() {
        return price - refund;
    }

    public TransactionVO putOnGroup() {
        this.onGroup = true;
        return this;
    }

    public boolean isAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(boolean alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
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
        dest.writeValue(this.refund);
        dest.writeParcelable(this.tag, flags);
        dest.writeParcelable(this.paymentType, flags);
        dest.writeString(this.content);
        dest.writeByte(this.clickable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.approved ? (byte) 1 : (byte) 0);
        dest.writeByte(this.onGroup ? (byte) 1 : (byte) 0);
        dest.writeString(this.parcels);
        dest.writeByte(this.alreadyPaid ? (byte) 1 : (byte) 0);
    }
}
