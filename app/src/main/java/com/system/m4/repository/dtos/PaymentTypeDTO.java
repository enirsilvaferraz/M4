package com.system.m4.repository.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class PaymentTypeDTO extends DTOAbs {

    @Expose
    private String key;

    @Expose
    private String name;

    @Expose
    private String color;

    public PaymentTypeDTO() {
        // Nothing to do
    }

    public PaymentTypeDTO(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentTypeDTO that = (PaymentTypeDTO) o;

        return key != null ? key.equals(that.key) : that.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
