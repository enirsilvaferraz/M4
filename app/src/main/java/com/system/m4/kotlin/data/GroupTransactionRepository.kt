package com.system.m4.kotlin.data

import com.system.m4.kotlin.model.entity.GroupTransactionModel
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by enirs on 30/09/2017.
 */
class GroupTransactionRepository @Inject constructor() : GenericRepository<GroupTransactionModel>("GroupTransaction") {
    override fun kClass(): KClass<GroupTransactionModel> = GroupTransactionModel::class
}