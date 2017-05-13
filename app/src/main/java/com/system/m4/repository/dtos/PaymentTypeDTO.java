package com.system.m4.repository.dtos;

import com.google.gson.annotations.Expose;
import com.system.m4.views.vos.PaymentTypeVO;

import java.util.Map;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class PaymentTypeDTO extends DTOAbs {

    @Expose
    private String key;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentTypeDTO that = (PaymentTypeDTO) o;

        return key.equals(that.key);

    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Expose
    private String name;

    public PaymentTypeDTO() {
        // Nothing to do
    }

    public PaymentTypeDTO(String key) {
        this.key = key;
    }

    public PaymentTypeDTO(PaymentTypeVO vo) {
        if (vo != null) {
            this.key = vo.getKey();
            this.name = vo.getName();
        }
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
