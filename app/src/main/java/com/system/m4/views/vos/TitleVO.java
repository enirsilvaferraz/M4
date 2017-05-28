package com.system.m4.views.vos;

import android.support.annotation.StringRes;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class TitleVO implements VOItemListInterface {

    private final int titleRes;

    public TitleVO(@StringRes int titleRes) {
        this.titleRes = titleRes;
    }

    public int getTitleRes() {
        return titleRes;
    }
}
