package com.system.m4.kotlin.paymenttype

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class PaymentTypeBusiness {

    companion object {

        fun save(model: PaymentTypeModel, listener: PersistenceListener<PaymentTypeModel>) {
            PaymentTypeRepository().save(model, listener)
        }

        fun update(model: PaymentTypeModel, listener: PersistenceListener<PaymentTypeModel>) {
            PaymentTypeRepository().update(model, listener)
        }

        fun delete(model: PaymentTypeModel, listener: PersistenceListener<PaymentTypeModel>) {
            PaymentTypeRepository().delete(model, listener)
        }

        fun findAll(listener: MultResultListener<PaymentTypeModel>) {
            PaymentTypeRepository().findAll("name", listener)
        }
    }
}