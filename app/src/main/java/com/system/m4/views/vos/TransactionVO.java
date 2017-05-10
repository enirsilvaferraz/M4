package com.system.m4.views.vos;

import android.os.Parcel;
import android.os.Parcelable;

import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;

import java.io.Serializable;

/**
 *
 */
public class TransactionVO implements Serializable, Parcelable {

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

    private String paymentDate;
    private String purchaseDate;
    private String price;
    private TagVO tag;
    private PaymentTypeVO paymentType;
    private String content;

    public TransactionVO() {
    }

    public TransactionVO(TransactionDTO dto, TagDTO tagDTO, PaymentTypeDTO paymentTypeDTO) {
        this.paymentDate = dto.getPaymentDate();
        this.purchaseDate = dto.getPurchaseDate();
        this.price = JavaUtils.NumberUtil.currencyFormat(dto.getPrice());
        this.tag = new TagVO(tagDTO);
        this.paymentType = new PaymentTypeVO(paymentTypeDTO);
        this.content = dto.getContent();
    }

    protected TransactionVO(Parcel in) {
        this.paymentDate = in.readString();
        this.purchaseDate = in.readString();
        this.price = in.readString();
        this.tag = in.readParcelable(TagVO.class.getClassLoader());
        this.paymentType = in.readParcelable(PaymentTypeVO.class.getClassLoader());
        this.content = in.readString();
    }

    public TransactionVO(TagVO tagVO) {
        this.tag = tagVO;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentDate);
        dest.writeString(this.purchaseDate);
        dest.writeString(this.price);
        dest.writeParcelable(this.tag, flags);
        dest.writeParcelable(this.paymentType, flags);
        dest.writeString(this.content);
    }
}