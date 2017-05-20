package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.FilterTransactionDTO;

/**
 * Created by Enir on 20/05/2017.
 * For M4
 */

public class FilterTransactionRepository extends FirebaseRepository<FilterTransactionDTO> {

    public FilterTransactionRepository() {
        super(FilterTransactionDTO.class.getSimpleName().replace("DTO", ""));
    }

    @Override
    protected Class<FilterTransactionDTO> getTClass() {
        return FilterTransactionDTO.class;
    }
}
