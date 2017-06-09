package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.GroupTransactionDTO;

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

public class GroupTransactionRepository extends FirebaseRepository<GroupTransactionDTO> {

    public GroupTransactionRepository() {
        super(GroupTransactionDTO.class.getSimpleName().replace("DTO", ""));
    }

    @Override
    protected Class<GroupTransactionDTO> getTClass() {
        return GroupTransactionDTO.class;
    }
}
