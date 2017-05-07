package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.TransactionFirebaseRepository;

import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 * For M4
 */

public abstract class TransactionBusinness {

    private TransactionBusinness() {
        // Nothing to do
    }

    public static void requestTransactions(final BusinnessListener.OnMultiResultListenner<TransactionDTO> onMultiResultListenner) {

        FilterTransactionBusinness.requestFilter(new BusinnessListener.OnSingleResultListener<FilterTransactionDTO>() {

            @Override
            public void onSuccess(FilterTransactionDTO dto) {

                new TransactionFirebaseRepository("dev").findByFilter(dto, new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

                    @Override
                    public void onFindAll(List<TransactionDTO> list) {
                        onMultiResultListenner.onSuccess(list);
                    }

                    @Override
                    public void onError(String error) {
                        onMultiResultListenner.onError(new Exception(error));
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                onMultiResultListenner.onError(e);
            }
        });
    }
}
