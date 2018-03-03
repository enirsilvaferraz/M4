package com.system.m4.kotlin.paymenttype

import com.system.m4.kotlin.infrastructure.GenericRepository
import kotlin.reflect.KClass

/**
 * Created by eferraz on 30/08/2017.
 * Repository para Tag
 */
class PaymentTypeRepository : GenericRepository<PaymentTypeModel>("PaymentType") {
    override fun kClass(): KClass<PaymentTypeModel> = PaymentTypeModel::class
}