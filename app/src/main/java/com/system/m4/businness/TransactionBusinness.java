package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.kotlin.home.FixedTransactionRepository;
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener;
import com.system.m4.kotlin.transaction.TransactionModel;
import com.system.m4.views.vos.Transaction;

/**
 * Created by Enir on 22/04/2017.
 * For M4
 */

public abstract class TransactionBusinness implements BusinnessInterface<Transaction> {

    private TransactionBusinness() {
        // Nothing to do
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
