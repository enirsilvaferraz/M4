package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.PaymentTypeFirebaseRepository;

import java.util.List;

public abstract class PaymentTypeBusinness {

    private PaymentTypeBusinness() {
        // Nothing to do
    }

    public static void requestPaymentTypeList(final BusinnessListener.OnMultiResultListenner onMultiResultListenner) {

        new PaymentTypeFirebaseRepository("dev").findAll(new FirebaseRepository.FirebaseMultiReturnListener<PaymentTypeDTO>() {

            @Override
            public void onFindAll(List<PaymentTypeDTO> list) {
                onMultiResultListenner.onSuccess(list);
            }

            @Override
            public void onError(String error) {
                onMultiResultListenner.onError(new Exception(error));
            }
        });
    }

    public static void save(PaymentTypeDTO dto, BusinnessListener.OnPersistListener persistListener) {

    }

    public static void delete(PaymentTypeDTO dto, BusinnessListener.OnPersistListener persistListener) {

    }
}