package com.system.m4.infrastructure;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public interface BusinnessListener {

    /**
     *
     */
    interface OnMultiResultListenner<T> {

        void onSuccess(List<T> list);

        void onError(Exception e);
    }

    /**
     *
     */
    interface OnSingleResultListener<T> {

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
