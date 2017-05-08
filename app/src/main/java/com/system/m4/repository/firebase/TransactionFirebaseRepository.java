package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 01/05/17.
 * For M4
 */

public class TransactionFirebaseRepository extends FirebaseRepository<TransactionDTO> {

    public TransactionFirebaseRepository(String flavor) {
        super(flavor, "Transaction");
    }

    public void findByFilter(FilterTransactionDTO filterTransactionDTO, FirebaseMultiReturnListener<TransactionDTO> firebaseMultiReturnListener) {

        final List<TransactionDTO> list = new ArrayList<>();
        firebaseMultiReturnListener.onFindAll(list);
    }

    @Override
    protected Class<TransactionDTO> getTClass() {
        return TransactionDTO.class;
    }
}
