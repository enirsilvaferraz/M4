package com.system.m4.kotlin.model.business

import com.system.m4.kotlin.data.PaymentTypeRepository
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.model.entity.PaymentTypeModel
import com.system.m4.labs.vos.PaymentTypeVO
import java.util.*
import javax.inject.Inject

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class PaymentTypeBusiness @Inject constructor(private val repository: PaymentTypeRepository) {

    fun save(model: PaymentTypeModel, listener: PersistenceListener<PaymentTypeModel>) {
        repository.save(model, listener)
    }

    fun update(model: PaymentTypeModel, listener: PersistenceListener<PaymentTypeModel>) {
        repository.update(model, listener)
    }

    fun delete(model: PaymentTypeModel, listener: PersistenceListener<PaymentTypeModel>) {
        repository.delete(model, listener)
    }

    fun findAll(listener: MultResultListener<PaymentTypeModel>) {
        repository.findAll("name", listener)
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