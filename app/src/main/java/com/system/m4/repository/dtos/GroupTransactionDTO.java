package com.system.m4.repository.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

public class GroupTransactionDTO extends DTOAbs {

    @Expose
    private String key;

    @Expose
    private List<String> listPaymentType;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public List<String>  getListPaymentType() {
        return listPaymentType;
    }

    public void setListPaymentType(List<String>  listPaymentType) {
        this.listPaymentType = listPaymentType;
    }
}
