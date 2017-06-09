package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

public class GroupTransactionVO implements VOItemListInterface {

    private List<PaymentTypeVO> paymentTypeList;

    public List<PaymentTypeVO> getPaymentTypeList() {
        return paymentTypeList;
    }

    public void setPaymentTypeList(List<PaymentTypeVO> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
    }
}
