package com.system.m4.repository.dtos;

import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.Map;

public class TransactionDTO extends DTOAbs {

    private String key;

    private String paymentDate;

    private String purchaseDate;

    private String price;

    private TagDTO tag;

    private String paymentType;

    private String content;

    public TransactionDTO() {
    }

    public TransactionDTO(TransactionVO vo, TagVO tagVO) {
        this.paymentDate = vo.getPaymentDate();
        this.purchaseDate = vo.getPurchaseDate();
        this.price = vo.getPrice();
        this.tag = new TagDTO(tagVO);
        this.paymentType = vo.getPaymentType();
        this.content = vo.getContent();
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TagDTO getTag() {
        return tag;
    }

    public void setTag(TagDTO tag) {
        this.tag = tag;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Map<String, String> getMapUpdate() {
        return null;
    }
}