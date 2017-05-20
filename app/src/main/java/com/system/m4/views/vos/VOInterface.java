package com.system.m4.views.vos;

import android.os.Parcelable;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public interface VOInterface<T extends VOInterface> extends Parcelable, Comparable<T> {

    String getName();

    void setName(String name);

    String getKey();
}
