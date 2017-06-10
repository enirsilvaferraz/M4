package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.FixedTransactionFirebaseRepository;
import com.system.m4.repository.firebase.TransactionFirebaseRepository;
import com.system.m4.views.vos.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 * For M4
 */

public abstract class TransactionBusinness implements BusinnessInterface<Transaction> {

    private TransactionBusinness() {
        // Nothing to do
    }

    public static void save(Transaction vo, final BusinnessListener.OnPersistListener persistListener) {
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

    public static void findByFilter(Date start, Date end, final BusinnessListener.OnMultiResultListenner<Transaction> listenner) {

        new TransactionFirebaseRepository().findByFilter(start, end, new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

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
