package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.repository.firebase.FilterTransactionRepository;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.TransactionFirebaseRepository;
import com.system.m4.views.vos.TransactionVO;

import java.util.ArrayList;
import java.util.Date;
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
        new TransactionFirebaseRepository().save(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

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

    public static void findByFilter(final BusinnessListener.OnMultiResultListenner<TransactionVO> multiResultListenner) {

        new FilterTransactionRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<FilterTransactionDTO>() {

            @Override
            public void onFindAll(List<FilterTransactionDTO> list) {

                final FilterTransactionDTO filterDTO;
                if (list.isEmpty()) {
                    Date actualMinimum = JavaUtils.DateUtil.getActualMinimum(new Date());
                    Date actualMaximum = JavaUtils.DateUtil.getActualMaximum(new Date());

                    filterDTO = new FilterTransactionDTO();
                    filterDTO.setPaymentDateStart(JavaUtils.DateUtil.format(actualMinimum, JavaUtils.DateUtil.YYYY_MM_DD));
                    filterDTO.setPaymentDateEnd(JavaUtils.DateUtil.format(actualMaximum, JavaUtils.DateUtil.YYYY_MM_DD));
                } else {
                    filterDTO = list.get(0);

                    if (filterDTO.getPaymentDateStart() == null) {
                        filterDTO.setPaymentDateStart("2015/01/01");
                    }
                    if (filterDTO.getPaymentDateEnd() == null) {
                        filterDTO.setPaymentDateEnd("2025/01/01");
                    }
                }

                findTransactions(filterDTO, multiResultListenner);
            }

            @Override
            public void onError(String error) {
                multiResultListenner.onError(new Exception(error));
            }
        });
    }

    private static void findTransactions(final FilterTransactionDTO filterDTO, final BusinnessListener.OnMultiResultListenner<TransactionVO> multiResultListenner) {

        final List<TransactionDTO> listTransaction = new ArrayList<>();
        final List<TagDTO> listTag = new ArrayList<>();
        final List<PaymentTypeDTO> listPaymentType = new ArrayList<>();

        Date paymentDateStart = filterDTO.getPaymentDateStart() != null ? JavaUtils.DateUtil.parse(filterDTO.getPaymentDateStart(), JavaUtils.DateUtil.YYYY_MM_DD) : null;
        Date paymentDateEnd = filterDTO.getPaymentDateEnd() != null ? JavaUtils.DateUtil.parse(filterDTO.getPaymentDateEnd(), JavaUtils.DateUtil.YYYY_MM_DD) : null;

        new TransactionFirebaseRepository().findByFilter(paymentDateStart, paymentDateEnd, new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

            @Override
            public void onFindAll(List<TransactionDTO> list) {

                if (list.isEmpty()) {
                    multiResultListenner.onSuccess(new ArrayList<TransactionVO>());
                }

                for (TransactionDTO transactionDTO : list) {
                    if ((JavaUtils.StringUtil.isEmpty(filterDTO.getTags()) || transactionDTO.getTag().equals(filterDTO.getTags())) &&
                            (JavaUtils.StringUtil.isEmpty(filterDTO.getPaymentType()) || transactionDTO.getPaymentType().equals(filterDTO.getPaymentType()))) {
                        listTransaction.add(transactionDTO);
                    }
                }
                configureList(listTransaction, listTag, listPaymentType, multiResultListenner);
            }

            @Override
            public void onError(String error) {
                multiResultListenner.onError(new Exception(error));
            }
        });

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                listTag.addAll(list);
                configureList(listTransaction, listTag, listPaymentType, multiResultListenner);
            }

            @Override
            public void onError(Exception e) {
                multiResultListenner.onError(e);
            }
        });

        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                listPaymentType.addAll(list);
                configureList(listTransaction, listTag, listPaymentType, multiResultListenner);
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

    public static void delete(TransactionDTO dto, final BusinnessListener.OnPersistListener listener) {

        new TransactionFirebaseRepository().delete(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

            @Override
            public void onFind(TransactionDTO dtoDeleted) {
                listener.onSuccess(dtoDeleted);
            }

            @Override
            public void onError(String error) {
                listener.onError(new Exception(error));
            }
        });
    }
}
