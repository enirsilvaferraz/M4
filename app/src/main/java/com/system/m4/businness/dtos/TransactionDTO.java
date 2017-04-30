package com.system.m4.businness.dtos;

public class TransactionDTO implements DTOInterface {

    private String paymentDate;

    private String purchaseDate;

    private String price;

    private String tag;

    private String paymentType;

    private String content;

    public TransactionDTO() {
    }

    public TransactionDTO(String paymentDate, String purchaseDate, String price, String tag, String paymentType, String content) {
        this.paymentDate = paymentDate;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.tag = tag;
        this.paymentType = paymentType;
        this.content = content;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}