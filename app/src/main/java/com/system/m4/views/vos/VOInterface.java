package com.system.m4.views.vos;

import android.os.Parcelable;

import com.system.m4.repository.dtos.DTOAbs;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public interface VOInterface extends Parcelable{

    String getName();

    void setName(String name);

    String getKey();
}
