package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.firebase.FilterTransactionRepository;
import com.system.m4.repository.firebase.FirebaseRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 20/05/2017.
 * For M4
 */

public class FilterTransactionBusinness {

    private FilterTransactionBusinness() {

    }

    public static void get(final BusinnessListener.OnSingleResultListener listener) {

        final FilterTransactionDTO dto = new FilterTransactionDTO();
        final List<TagDTO> listTag = new ArrayList<>();
        final List<PaymentTypeDTO> listPaymentType = new ArrayList<>();

        new FilterTransactionRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<FilterTransactionDTO>() {

            @Override
            public void onFindAll(List<FilterTransactionDTO> list) {
                if (!list.isEmpty()) {
                    FilterTransactionDTO localdto = list.get(0);
                    dto.setKey(localdto.getKey());
                    dto.setPaymentDateStart(localdto.getPaymentDateStart());
                    dto.setPaymentDateEnd(localdto.getPaymentDateEnd());
                    dto.setTags(localdto.getTags());
                    dto.setPaymentType(localdto.getPaymentType());
                    configureList(dto, listTag, listPaymentType, listener);
                } else {
                    listener.onSuccess(null);
                }
            }

            @Override
            public void onError(String error) {
                listener.onError(new Exception(error));
            }
        });

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                if (!list.isEmpty()) {
                    listTag.addAll(list);
                    configureList(dto, listTag, listPaymentType, listener);
                } else {
                    onError(new Exception(Constants.NOT_FOUND));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });

        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                if (!list.isEmpty()) {
                    listPaymentType.addAll(list);
                    configureList(dto, listTag, listPaymentType, listener);
                } else {
                    onError(new Exception(Constants.NOT_FOUND));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });
    }

    private static void configureList(FilterTransactionDTO dto, List<TagDTO> listTag, List<PaymentTypeDTO> listPaymentType, BusinnessListener.OnSingleResultListener listener) {
        if (!JavaUtils.StringUtil.isEmpty(dto.getKey()) && !listTag.isEmpty() && !listPaymentType.isEmpty()) {
            listener.onSuccess(ConverterUtils.fromFilterTransaction(dto, listTag, listPaymentType));
        }
    }

    public static void save(FilterTransactionDTO dto, final BusinnessListener.OnPersistListener listener) {

        new FilterTransactionRepository().save(dto, new FirebaseRepository.FirebaseSingleReturnListener<FilterTransactionDTO>() {

            @Override
            public void onFind(FilterTransactionDTO dtoParam) {
                listener.onSuccess(dtoParam);
            }

            @Override
            public void onError(String error) {
                listener.onError(new Exception(error));
            }
        });
    }

    public static void delete(FilterTransactionDTO dto, final BusinnessListener.OnPersistListener listener) {

        new FilterTransactionRepository().delete(dto, new FirebaseRepository.FirebaseSingleReturnListener<FilterTransactionDTO>() {

            @Override
            public void onFind(FilterTransactionDTO dtoParam) {
                listener.onSuccess(dtoParam);
            }

            @Override
            public void onError(String error) {
                listener.onError(new Exception(error));
            }
        });
    }
}
