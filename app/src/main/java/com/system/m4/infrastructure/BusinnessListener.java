package com.system.m4.infrastructure;

import com.system.m4.businness.dtos.DTOInterface;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public interface BusinnessListener {

    /**
     *
     */
    interface OnMultiResultListenner<T extends DTOInterface> {

        void onSuccess(List<T> list);

        void onError(Exception e);
    }

    /**
     *
     */
    interface OnSingleResultListener<T extends DTOInterface> {

        void onSuccess(T dto);

        void onError(Exception e);
    }

    /**
     *
     */
    interface OnPersistListener {

        void onSuccess();

        void onError(Exception e);
    }

}
