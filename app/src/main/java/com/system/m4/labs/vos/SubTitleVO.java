package com.system.m4.labs.vos;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class SubTitleVO implements VOItemListInterface {

    private final String titleRes;

    public SubTitleVO(String titleRes) {
        this.titleRes = titleRes;
    }

    public String getTitleRes() {
        return titleRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubTitleVO subTitleVO = (SubTitleVO) o;

        return titleRes.equals(subTitleVO.titleRes);

    }

    @Override
    public int hashCode() {
        return titleRes.hashCode();
    }
}
