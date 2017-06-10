package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.firebase.FilterTransactionRepository;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Enir on 20/05/2017.
 * For M4
 */

public class FilterTransactionBusinness {

    private FilterTransactionBusinness() {

    }

    public static void find(final BusinnessListener.OnSingleResultListener<FilterTransactionVO> listener) {

        final FilterTransactionDTO dto = new FilterTransactionDTO();
        final List<TagVO> listTag = new ArrayList<>();
        final List<PaymentTypeVO> listPaymentType = new ArrayList<>();

        new FilterTransactionRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<FilterTransactionDTO>() {

            @Override
            public void onFindAll(List<FilterTransactionDTO> list) {
                if (!list.isEmpty()) {
                    FilterTransactionDTO localdto = list.get(0);
                    dto.setKey(localdto.getKey());
                    dto.setPaymentDateStart(localdto.getPaymentDateStart());
                    dto.setPaymentDateEnd(localdto.getPaymentDateEnd());
                    dto.setTag(localdto.getTag());
                    dto.setPaymentType(localdto.getPaymentType());
                    configureList(dto, listTag, listPaymentType, listener);
                } else {
                    FilterTransactionVO vo = new FilterTransactionVO();
                    vo.setPaymentDateStart(JavaUtils.DateUtil.getActualMinimum(Calendar.getInstance().getTime()));
                    vo.setPaymentDateEnd(JavaUtils.DateUtil.getActualMaximum(Calendar.getInstance().getTime()));
                    listener.onSuccess(vo);
                }
            }

            @Override
            public void onError(String error) {
                listener.onError(new Exception(error));
            }
        });

        TagBusinness.findAll(new BusinnessListener.OnMultiResultListenner<TagVO>() {

            @Override
            public void onSuccess(List<TagVO> list, int call) {
                listTag.addAll(list);
                configureList(dto, listTag, listPaymentType, listener);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });

        PaymentTypeBusinness.findAll(new BusinnessListener.OnMultiResultListenner<PaymentTypeVO>() {

            @Override
            public void onSuccess(List<PaymentTypeVO> list, int call) {
                listPaymentType.addAll(list);
                configureList(dto, listTag, listPaymentType, listener);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });
    }

    private static void configureList(FilterTransactionDTO dto, List<TagVO> listTag, List<PaymentTypeVO> listPaymentType, BusinnessListener.OnSingleResultListener<FilterTransactionVO> listener) {
        if (!JavaUtils.StringUtil.isEmpty(dto.getKey()) && !listTag.isEmpty() && !listPaymentType.isEmpty()) {

            FilterTransactionVO filter = ConverterUtils.fromFilterTransaction(dto, null, null);
            if (filter.getPaymentDateStart() == null) {
                filter.setPaymentDateStart(JavaUtils.DateUtil.getDate(2015, 01, 01));
            }
            if (filter.getPaymentDateEnd() == null) {
                filter.setPaymentDateEnd(JavaUtils.DateUtil.getDate(2025, 01, 01));
            }

            listener.onSuccess(ConverterUtils.fillFilterTransaction(filter, listTag, listPaymentType));
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
