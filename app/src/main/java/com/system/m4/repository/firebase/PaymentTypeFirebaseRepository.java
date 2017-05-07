package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.PaymentTypeDTO;

/**
 * Created by eferraz on 01/05/17.
 * For M4
 */

public class PaymentTypeFirebaseRepository extends FirebaseRepository<PaymentTypeDTO> {

    public PaymentTypeFirebaseRepository(String flavor) {
        super(flavor, "PaymentType");
    }

    @Override
    protected Class<PaymentTypeDTO> getTClass() {
        return PaymentTypeDTO.class;
    }
}
