package com.system.m4.views.vos;

import android.os.Parcelable;

import com.system.m4.repository.dtos.DTOAbs;

/**
 * Created by eferraz on 05/12/15.
 * For AndroidPigBank
 */
public abstract class VOAbs implements Parcelable, Cloneable {

    public abstract DTOAbs toDTO();

}
