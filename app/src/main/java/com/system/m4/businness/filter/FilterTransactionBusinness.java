package com.system.m4.businness.filter;

import com.system.m4.infrastructure.BusinnessListener;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public class FilterTransactionBusinness {

    public static void persistFilter(BusinnessListener.OnPersistListener persistListener) {
        persistListener.onSuccess();
    }
}
