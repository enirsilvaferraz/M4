package com.system.m4.infrastructure

import com.system.m4.repository.dtos.GroupTransactionDTO
import com.system.m4.views.vos.GroupTransactionVO
import com.system.m4.views.vos.PaymentTypeVO
import java.util.*

/**
 * Created by eferraz on 10/05/17.
 * For M4
 */

object ConverterUtils {

    fun fromGroupTransaction(dto: GroupTransactionDTO): GroupTransactionVO {
        val vo = GroupTransactionVO()
        vo.paymentTypeList = ArrayList()
        vo.key = dto.key
        for (key in dto.listPaymentType) {
            val paymentTypeVO = PaymentTypeVO()
            paymentTypeVO.key = key
            vo.paymentTypeList.add(paymentTypeVO)
        }
        return vo
    }

    fun fillGroupTransaction(group: GroupTransactionVO, listPaymentType: List<PaymentTypeVO>): GroupTransactionVO {
        for (typeVO in group.paymentTypeList) {
            typeVO.name = listPaymentType[listPaymentType.indexOf(typeVO)].name
        }
        return group
    }

    fun fromGroupTransaction(list: ArrayList<GroupTransactionDTO>): ArrayList<GroupTransactionVO> {
        val listVO = ArrayList<GroupTransactionVO>()
        for (model in list) {
            listVO.add(fromGroupTransaction(model))
        }
        return listVO
    }
}// Do nothing
