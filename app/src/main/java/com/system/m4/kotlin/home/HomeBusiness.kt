package com.system.m4.kotlin.home

import com.system.m4.kotlin.group.GroupTransactionBusiness
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.SingleResultListener
import com.system.m4.kotlin.paymenttype.PaymentTypeBusiness
import com.system.m4.kotlin.paymenttype.PaymentTypeModel
import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.kotlin.transaction.TransactionBusiness
import com.system.m4.views.vos.GroupTransactionVO
import com.system.m4.views.vos.ListTransactionVO
import com.system.m4.views.vos.TransactionVO

/**
 * Created by enirs on 30/09/2017.
 * Business para Home
 */
class HomeBusiness {

    fun findHomeList(year: Int, month: Int, listener: SingleResultListener<ListTransactionVO>) {

        val homeVo = HomeDTO(year, month)

        TransactionBusiness.findAll(year, month, object : ErrorListener<TransactionVO>(listener) {
            override fun onSuccess(list: ArrayList<TransactionVO>) {
                homeVo.listTransaction = list
                validate(homeVo, listener)
            }
        })

        TagBusiness.findAll(object : ErrorListener<TagModel>(listener) {
            override fun onSuccess(list: ArrayList<TagModel>) {
                homeVo.listTag = TagBusiness.fromTag(list)
                validate(homeVo, listener)
            }
        })

        PaymentTypeBusiness.findAll(object : ErrorListener<PaymentTypeModel>(listener) {
            override fun onSuccess(list: ArrayList<PaymentTypeModel>) {
                homeVo.listPaymentType = PaymentTypeBusiness.fromPaymentType(list)
                validate(homeVo, listener)
            }
        })

        GroupTransactionBusiness.findAll(object : ErrorListener<GroupTransactionVO>(listener) {
            override fun onSuccess(list: ArrayList<GroupTransactionVO>) {
                homeVo.listGroup = list
                validate(homeVo, listener)
            }
        })
    }

    private fun validate(homeDTO: HomeDTO, listener: SingleResultListener<ListTransactionVO>) {

        val listTransaction = homeDTO.listTransaction
        val listTag = homeDTO.listTag
        val listPaymentType = homeDTO.listPaymentType
        val listGroup = homeDTO.listGroup

        if (listTransaction != null && listTag != null && listPaymentType != null && listGroup != null) {

//            for (transaction in listTransaction) {
//                TransactionBusiness.fillTransaction(transaction, listTag, listPaymentType)
//            }

            listTransaction.forEach { TransactionBusiness.fillTransaction(it, listTag, listPaymentType) }

            val listTransactionVO = ListTransactionVO()
            listTransactionVO.tagSummary = TagBusiness.calculateTagSummary(listTransaction)
            listTransactionVO.transactions = listTransaction
            listTransactionVO.pendingTransaction = getPendingTransaction(listTransaction)

            if (!listGroup.isEmpty()) {
                listTransactionVO.group = GroupTransactionBusiness.fillGroupTransaction(listGroup.get(0), listPaymentType)
            }

            listener.onSuccess(listTransactionVO)
        }
    }

    private fun getPendingTransaction(listTransaction: ArrayList<TransactionVO>): MutableList<TransactionVO>? {
        val pendingList = arrayListOf<TransactionVO>()
        for (transaction in listTransaction) {
            if (transaction.tag.key.isNullOrBlank()) {
                pendingList.add(transaction)
            }
        }
        return pendingList
    }

    abstract class ErrorListener<T>(val listener: SingleResultListener<ListTransactionVO>) : MultResultListener<T> {
        override fun onError(error: String) {
            listener.onError(error)
        }
    }
}