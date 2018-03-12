package com.system.m4.kotlin.data

import com.system.m4.kotlin.model.entity.TransactionModel
import kotlin.reflect.KClass

/**
 * Created by enirs on 30/09/2017.
 * Repository for TransactionVO
 */
class TransactionRepository(val year: Int, val month: Int) : GenericRepository<TransactionModel>("/Register/$year/${(month + 1)}/Transaction/") {
    override fun kClass(): KClass<TransactionModel> = TransactionModel::class
}