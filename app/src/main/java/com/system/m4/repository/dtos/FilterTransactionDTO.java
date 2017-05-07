package com.system.m4.repository.dtos;

/**
 *
 */
public class FilterTransactionDTO {

    private String paymentDateStart;
    private String paymentDateEnd;
    private String tags;
    private String paymentType;

    public FilterTransactionDTO(String paymentDateStart, String paymentDateEnd, String tags, String paymentType) {
        this.paymentDateStart = paymentDateStart;
        this.paymentDateEnd = paymentDateEnd;
        this.tags = tags;
        this.paymentType = paymentType;
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

}
