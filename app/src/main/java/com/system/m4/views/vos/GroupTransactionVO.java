package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

public class GroupTransactionVO implements VOItemListInterface {

    private String key;

    private List<PaymentTypeVO> paymentTypeList;

    public List<PaymentTypeVO> getPaymentTypeList() {
        return paymentTypeList;
    }

    public void setPaymentTypeList(List<PaymentTypeVO> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupTransactionVO that = (GroupTransactionVO) o;

        return key.equals(that.key);

    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
