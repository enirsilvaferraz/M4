package com.system.m4.kotlin.data

import com.system.m4.kotlin.model.entity.PaymentTypeModel
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by eferraz on 30/08/2017.
 * Repository para Tag
 */
class PaymentTypeRepository @Inject constructor() : GenericRepository<PaymentTypeModel>("PaymentType") {
    override fun kClass(): KClass<PaymentTypeModel> = PaymentTypeModel::class
}