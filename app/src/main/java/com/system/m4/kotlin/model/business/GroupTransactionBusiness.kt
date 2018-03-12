package com.system.m4.kotlin.model.business

import com.system.m4.kotlin.data.GroupTransactionRepository
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.model.entity.GroupTransactionModel
import com.system.m4.labs.vos.GroupTransactionVO
import com.system.m4.labs.vos.PaymentTypeVO
import javax.inject.Inject

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

class GroupTransactionBusiness @Inject constructor() {

    fun findAll(onMultiResultListenner: MultResultListener<GroupTransactionVO>) {

        GroupTransactionRepository().findAll(listener = object : MultResultListener<GroupTransactionModel> {
            override fun onSuccess(list: ArrayList<GroupTransactionModel>) {
                val listVo = ArrayList<GroupTransactionVO>()
                for (dto in list) {
                    listVo.add(fromGroupTransaction(dto))
                }
                onMultiResultListenner.onSuccess(listVo)
            }

            override fun onError(error: String) {
                onMultiResultListenner.onError(error)
            }
        })
    }

    fun fillGroupTransaction(group: GroupTransactionVO, listPaymentType: List<PaymentTypeVO>): GroupTransactionVO {

        group.paymentTypeList.forEach {

            val item = listPaymentType[listPaymentType.indexOf(it)]

            it.key = item.key
            it.name = item.name
            it.color = item.color
        }

        return group
    }

    fun fromGroupTransaction(model: GroupTransactionModel): GroupTransactionVO {

        val vo = GroupTransactionVO()
        vo.paymentTypeList = java.util.ArrayList()
        vo.key = model.key

        for (key in model.listPaymentType!!) {
            val paymentTypeVO = PaymentTypeVO()
            paymentTypeVO.key = key
            vo.paymentTypeList.add(paymentTypeVO)
        }

        return vo
    }

    fun fromGroupTransaction(list: java.util.ArrayList<GroupTransactionModel>): java.util.ArrayList<GroupTransactionVO> {
        val listVO = java.util.ArrayList<GroupTransactionVO>()
        for (model in list) {
            listVO.add(fromGroupTransaction(model))
        }
        return listVO
    }
}
