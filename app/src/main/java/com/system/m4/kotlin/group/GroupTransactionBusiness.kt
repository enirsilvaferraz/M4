package com.system.m4.kotlin.group

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.views.vos.GroupTransactionVO
import com.system.m4.views.vos.PaymentTypeVO

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

object GroupTransactionBusiness {

    fun findAll(onMultiResultListenner: MultResultListener<GroupTransactionVO>) {

        GroupTransactionRepository.findAll(object : MultResultListener<GroupTransactionDTO> {
            override fun onSuccess(list: ArrayList<GroupTransactionDTO>) {
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

    fun fromGroupTransaction(dto: GroupTransactionDTO): GroupTransactionVO {

        val vo = GroupTransactionVO()
        vo.paymentTypeList = java.util.ArrayList()
        vo.key = dto.key

        for (key in dto.listPaymentType!!) {
            val paymentTypeVO = PaymentTypeVO()
            paymentTypeVO.key = key
            vo.paymentTypeList.add(paymentTypeVO)
        }

        return vo
    }

    fun fromGroupTransaction(list: java.util.ArrayList<GroupTransactionDTO>): java.util.ArrayList<GroupTransactionVO> {
        val listVO = java.util.ArrayList<GroupTransactionVO>()
        for (model in list) {
            listVO.add(fromGroupTransaction(model))
        }
        return listVO
    }
}
