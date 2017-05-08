package com.system.m4.repository.dtos;

import com.system.m4.views.vos.PaymentTypeVO;

import java.util.Map;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class PaymentTypeDTO extends DTOAbs {

    private String key;

    private String name;

    public PaymentTypeDTO() {
        // Nothing to do
    }

    public PaymentTypeDTO(String name) {
        this.name = name;
    }

    public PaymentTypeDTO(PaymentTypeVO vo) {
        this.key = vo.getKey();
        this.name = vo.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
