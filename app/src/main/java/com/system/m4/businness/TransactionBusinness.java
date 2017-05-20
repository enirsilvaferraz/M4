package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.TransactionFirebaseRepository;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 * For M4
 */

public abstract class TransactionBusinness {

    private TransactionBusinness() {
        // Nothing to do
    }

    public static void save(TransactionVO vo, final BusinnessListener.OnPersistListener persistListener) {
        TransactionDTO dto = ConverterUtils.fromTransaction(vo);
        new TransactionFirebaseRepository("dev").save(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

            @Override
            public void onFind(TransactionDTO dto) {
                persistListener.onSuccess(dto);
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }

    public static void findByFilter(FilterTransactionVO vo, final BusinnessListener.OnMultiResultListenner<TransactionVO> multiResultListenner) {

        final List<TransactionDTO> listTransaction = new ArrayList<>();
        final List<TagDTO> listTag = new ArrayList<>();
        final List<PaymentTypeDTO> listPaymentType = new ArrayList<>();

        new TransactionFirebaseRepository("dev").findByFilter(vo.getPaymentDateStart(), vo.getPaymentDateEnd(), new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

            @Override
            public void onFindAll(List<TransactionDTO> list) {
                if (!list.isEmpty()) {
                    listTransaction.addAll(list);
                    configureList(listTransaction, listTag, listPaymentType, multiResultListenner);
                } else {
                    onError(Constants.NOT_FOUND);
                }
            }

            @Override
            public void onError(String error) {
                multiResultListenner.onError(new Exception(error));
            }
        });

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                if (!list.isEmpty()) {
                    listTag.addAll(list);
                    configureList(listTransaction, listTag, listPaymentType, multiResultListenner);
                } else {
                    onError(new Exception(Constants.NOT_FOUND));
                }
            }

            @Override
            public void onError(Exception e) {
                multiResultListenner.onError(e);
            }
        });

        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                if (!list.isEmpty()) {
                    listPaymentType.addAll(list);
                    configureList(listTransaction, listTag, listPaymentType, multiResultListenner);
                } else {
                    onError(new Exception(Constants.NOT_FOUND));
                }
            }

            @Override
            public void onError(Exception e) {
                multiResultListenner.onError(e);
            }
        });

    }

    private static void configureList(List<TransactionDTO> listTransaction, List<TagDTO> listTag, List<PaymentTypeDTO> listPaymentType, BusinnessListener.OnMultiResultListenner<TransactionVO> multiResultListenner) {

        if (!listTransaction.isEmpty() && !listTag.isEmpty() && !listPaymentType.isEmpty()) {

            List<TransactionVO> listVo = new ArrayList<>();
            for (TransactionDTO dto : listTransaction) {
                listVo.add(ConverterUtils.fromTransaction(dto, listTag, listPaymentType));
            }

            multiResultListenner.onSuccess(listVo);
        }
    }
}
