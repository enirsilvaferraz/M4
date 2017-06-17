package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.TransactionDTO;

/**
 * Created by eferraz on 10/06/17.
 * For M4
 */

public class FixedTransactionFirebaseRepository extends FirebaseRepository<TransactionDTO> {

    public FixedTransactionFirebaseRepository() {
        super("FixedTransaction");
    }

    @Override
    protected Class<TransactionDTO> getTClass() {
        return TransactionDTO.class;
    }
}
