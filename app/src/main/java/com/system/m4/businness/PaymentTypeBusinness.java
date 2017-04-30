package com.system.m4.businness;

import com.system.m4.businness.dtos.DTOInterface;
import com.system.m4.businness.dtos.PaymentTypeDTO;
import com.system.m4.infrastructure.BusinnessListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentTypeBusinness {
    public static void requestPaymentTypeList(BusinnessListener.OnMultiResultListenner onMultiResultListenner) {

        List<DTOInterface> list = new ArrayList<DTOInterface>();
        list.add(new PaymentTypeDTO("Nubank"));
        list.add(new PaymentTypeDTO("Dinheiro"));
        list.add(new PaymentTypeDTO("Itaucard"));
        list.add(new PaymentTypeDTO("Transf. Itaú"));
        list.add(new PaymentTypeDTO("Transf. Bradesco"));

        onMultiResultListenner.onSuccess(list);
    }
}