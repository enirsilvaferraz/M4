package com.system.m4.views.vos;

import java.util.Objects;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class AmountVO implements VOItemListInterface {

    private final Double value;

    public AmountVO(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountVO amountVO = (AmountVO) o;
        return Objects.equals(value, amountVO.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}