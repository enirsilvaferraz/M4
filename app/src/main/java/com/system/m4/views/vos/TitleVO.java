package com.system.m4.views.vos;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class TitleVO implements VOItemListInterface {

    private final String titleRes;

    public TitleVO(String titleRes) {
        this.titleRes = titleRes;
    }

    public String getTitleRes() {
        return titleRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleVO titleVO = (TitleVO) o;

        return titleRes.equals(titleVO.titleRes);

    }

    @Override
    public int hashCode() {
        return titleRes.hashCode();
    }
}
