package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener;
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener;
import com.system.m4.kotlin.transaction.FixedTransactionRepository;
import com.system.m4.kotlin.transaction.TransactionModel;
import com.system.m4.kotlin.transaction.TransactionRepository;
import com.system.m4.views.vos.Transaction;

import org.jetbrains.annotations.NotNull;

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

        TransactionModel dto = ConverterUtils.fromTransaction(vo);

        if (dto.getKey() == null || isInPath(vo)) {

            new TransactionRepository(year, month).create(dto, new PersistenceListener<TransactionModel>() {

                @Override
                public void onSuccess(TransactionModel model) {
                    if (persistListener != null) {
                        persistListener.onSuccess(model);
                    }
                }

                @Override
                public void onError(String error) {
                    if (persistListener != null) {
                        persistListener.onError(new Exception(error));
                    }
                }
            });

        } else {

            final int yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDateOrigin());
            final int monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDateOrigin());

            new TransactionRepository(yearOrigin, monthOrigin).delete(dto, new PersistenceListener<TransactionModel>() {

                @Override
                public void onSuccess(TransactionModel model) {

                    new TransactionRepository(year, month).create(model, new PersistenceListener<TransactionModel>() {

                        @Override
                        public void onSuccess(TransactionModel model) {
                            if (persistListener != null) {
                                persistListener.onSuccess(model);
                            }
                        }

                        @Override
                        public void onError(String error) {
                            if (persistListener != null) {
                                persistListener.onError(new Exception(error));
                            }
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    if (persistListener != null) {
                        persistListener.onError(new Exception(error));
                    }
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

        TransactionModel dto = ConverterUtils.fromTransaction(vo);

        int year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.getPaymentDate());
        int month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.getPaymentDate());

        new TransactionRepository(year, month).delete(dto, new PersistenceListener<TransactionModel>() {

            @Override
            public void onSuccess(TransactionModel dtoDeleted) {
                listener.onSuccess(dtoDeleted);
            }

            @Override
            public void onError(String error) {
                listener.onError(new Exception(error));
            }
        });
    }

    public static void findByFilter(Integer year, Integer month, final BusinnessListener.OnMultiResultListenner<Transaction> listenner) {

        new TransactionRepository(year, month).findAll(new MultResultListener<TransactionModel>() {

            @Override
            public void onSuccess(@NotNull ArrayList<TransactionModel> list) {
                List<Transaction> listVo = new ArrayList<>();
                for (TransactionModel dto : list) {
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

        new FixedTransactionRepository().findAll(new MultResultListener<TransactionModel>() {

            @Override
            public void onSuccess(ArrayList<TransactionModel> list) {
                List<Transaction> listVo = new ArrayList<>();
                for (TransactionModel dto : list) {
                    Transaction transaction = ConverterUtils.fromTransaction(dto);
                    transaction.setPinned(true);
                    transaction.setApproved(false);
                    listVo.add(transaction);
                }
                listenner.onSuccess(listVo, Constants.CALL_TRANSACTION_FIXED);
            }

            @Override
            public void onError(String error) {
                listenner.onError(new Exception(error));
            }
        });

    }

    public static void pin(Transaction transaction, final BusinnessListener.OnPersistListener<Transaction> persistListener) {

        new FixedTransactionRepository().create(ConverterUtils.fromTransaction(transaction), new PersistenceListener<TransactionModel>() {

            @Override
            public void onSuccess(TransactionModel dto) {
                persistListener.onSuccess(ConverterUtils.fromTransaction(dto));
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }

    public static void unpin(Transaction transaction, final BusinnessListener.OnPersistListener<Transaction> persistListener) {

        new FixedTransactionRepository().delete(ConverterUtils.fromTransaction(transaction), new PersistenceListener<TransactionModel>() {

            @Override
            public void onSuccess(TransactionModel dto) {
                persistListener.onSuccess(ConverterUtils.fromTransaction(dto));
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }
}
