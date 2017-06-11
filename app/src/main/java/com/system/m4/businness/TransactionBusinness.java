package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.FixedTransactionFirebaseRepository;
import com.system.m4.repository.firebase.TransactionFirebaseRepository;
import com.system.m4.views.vos.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 * For M4
 */

public abstract class TransactionBusinness implements BusinnessInterface<Transaction> {

    private TransactionBusinness() {
        // Nothing to do
    }

    public static void save(final Transaction vo, final BusinnessListener.OnPersistListener persistListener) {

        final int year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDate());
        final int month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDate());

        TransactionDTO dto = ConverterUtils.fromTransaction(vo);

        if (dto.getKey() == null || isInPath(vo)) {

            new TransactionFirebaseRepository(year, month).save(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

                @Override
                public void onFind(TransactionDTO dto) {
                    persistListener.onSuccess(dto);
                }

                @Override
                public void onError(String error) {
                    persistListener.onError(new Exception(error));
                }
            });

        } else {

            final int yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDateOrigin());
            final int monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDateOrigin());

            new TransactionFirebaseRepository(yearOrigin, monthOrigin).delete(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

                @Override
                public void onFind(TransactionDTO dto) {

                    dto.setKey(null);
                    new TransactionFirebaseRepository(year, month).save(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

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

                @Override
                public void onError(String error) {
                    persistListener.onError(new Exception(error));
                }
            });
        }
    }

    private static boolean isInPath(Transaction vo) {

        Integer year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDate());
        Integer month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDate());

        Integer yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDateOrigin());
        Integer monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDateOrigin());

        return year.equals(yearOrigin) && month.equals(monthOrigin);
    }


    public static void delete(Transaction vo, final BusinnessListener.OnPersistListener listener) {

        TransactionDTO dto = ConverterUtils.fromTransaction(vo);

        int year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDate());
        int month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDate());

        new TransactionFirebaseRepository(year, month).delete(dto, new FirebaseRepository.FirebaseSingleReturnListener<TransactionDTO>() {

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

    public static void findByFilter(Integer year, Integer month, final BusinnessListener.OnMultiResultListenner<Transaction> listenner) {

        new TransactionFirebaseRepository(year, month).findByFilter(new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

            @Override
            public void onFindAll(List<TransactionDTO> list) {
                List<Transaction> listVo = new ArrayList<>();
                for (TransactionDTO dto : list) {
                    listVo.add(ConverterUtils.fromTransaction(dto));
                }
                listenner.onSuccess(listVo, Constants.CALL_TRANSACTION_BY_FILTER);
            }

            @Override
            public void onError(String error) {
                listenner.onError(new Exception(error));
            }
        });
    }

    public static void findFixed(final BusinnessListener.OnMultiResultListenner<Transaction> listenner) {

        new FixedTransactionFirebaseRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

            @Override
            public void onFindAll(List<TransactionDTO> list) {
                List<Transaction> listVo = new ArrayList<>();
                for (TransactionDTO dto : list) {
                    listVo.add(ConverterUtils.fromTransaction(dto));
                }
                listenner.onSuccess(listVo, Constants.CALL_TRANSACTION_FIXED);
            }

            @Override
            public void onError(String error) {
                listenner.onError(new Exception(error));
            }
        });

    }

    public static void pin(Transaction transaction, BusinnessListener.OnPersistListener persistListener) {
        persistListener.onError(new Exception("Pin not implementede yet"));
    }
}
