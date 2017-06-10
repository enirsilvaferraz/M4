package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.PaymentTypeFirebaseRepository;
import com.system.m4.views.vos.PaymentTypeVO;

import java.util.ArrayList;
import java.util.List;

public abstract class PaymentTypeBusinness {

    private PaymentTypeBusinness() {
        // Nothing to do
    }

    public static void findAll(final BusinnessListener.OnMultiResultListenner<PaymentTypeVO> onMultiResultListenner) {

        new PaymentTypeFirebaseRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<PaymentTypeDTO>() {

            @Override
            public void onFindAll(List<PaymentTypeDTO> list) {
                List<PaymentTypeVO> listVO = new ArrayList<>();
                for (PaymentTypeDTO dto : list) {
                    listVO.add(ConverterUtils.fromPaymentType(dto));
                }
                onMultiResultListenner.onSuccess(listVO, Constants.CALL_PAYMENTTYPE_FINDALL);
            }

            @Override
            public void onError(String error) {
                onMultiResultListenner.onError(new Exception(error));
            }
        });
    }

    public static void save(PaymentTypeDTO dto, final BusinnessListener.OnPersistListener persistListener) {
        new PaymentTypeFirebaseRepository().save(dto, new FirebaseRepository.FirebaseSingleReturnListener<PaymentTypeDTO>() {

            @Override
            public void onFind(PaymentTypeDTO dto) {
                persistListener.onSuccess(dto);
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }

    public static void delete(PaymentTypeDTO dto, final BusinnessListener.OnPersistListener persistListener) {
        new PaymentTypeFirebaseRepository().delete(dto, new FirebaseRepository.FirebaseSingleReturnListener<PaymentTypeDTO>() {

            @Override
            public void onFind(PaymentTypeDTO dto) {
                persistListener.onSuccess(dto);
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }
}