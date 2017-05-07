package com.system.m4.businness;

import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.views.vos.FilterTransactionVO;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public class FilterTransactionBusinness {

    public static void persistFilter(FilterTransactionVO mVo, BusinnessListener.OnPersistListener persistListener) {
        persistListener.onSuccess();
    }

    public static void requestFilter(BusinnessListener.OnSingleResultListener<FilterTransactionDTO> onSingleResultListener) {
        onSingleResultListener.onSuccess(new FilterTransactionDTO("02/01/2017", "02/01/2017", "Moradia", "Nubank"));
    }
}
