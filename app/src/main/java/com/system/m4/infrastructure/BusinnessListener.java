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
    interface OnMultiResultListenner {

        void onSuccess(List<String> list);

        void onError(Exception e);
    }

    /**
     *
     */
    interface OnSingleResultListener {

        void onSuccess(String item);

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
