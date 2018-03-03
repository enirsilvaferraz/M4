package com.system.m4.kotlin.paymenttype

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.views.vos.PaymentTypeVO
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class PaymentTypeBusiness {

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

    fun fromPaymentType(dto: PaymentTypeModel): PaymentTypeVO {
        val vo = PaymentTypeVO()
        vo.key = dto.key
        vo.name = dto.name
        vo.color = dto.color
        return vo
    }

    fun fromPaymentType(vo: PaymentTypeVO): PaymentTypeModel {
        val dto = PaymentTypeModel()
        dto.key = vo.key
        dto.name = vo.name
        dto.color = vo.color
        return dto
    }

    fun fromPaymentType(list: ArrayList<PaymentTypeModel>): ArrayList<PaymentTypeVO> {
        val listVO = ArrayList<PaymentTypeVO>()
        for (model in list) {
            listVO.add(fromPaymentType(model))
        }
        return listVO
    }
}