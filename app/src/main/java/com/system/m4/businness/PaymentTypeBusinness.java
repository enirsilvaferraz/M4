package com.system.m4.businness;

import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.infrastructure.BusinnessListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentTypeBusinness {
    public static void requestPaymentTypeList(BusinnessListener.OnMultiResultListenner onMultiResultListenner) {

        List<DTOAbs> list = new ArrayList<DTOAbs>();
        list.add(new PaymentTypeDTO("Nubank"));
        list.add(new PaymentTypeDTO("Dinheiro"));
        list.add(new PaymentTypeDTO("Itaucard"));
        list.add(new PaymentTypeDTO("Transf. Itaú"));
        list.add(new PaymentTypeDTO("Transf. Bradesco"));

        onMultiResultListenner.onSuccess(list);
    }
}