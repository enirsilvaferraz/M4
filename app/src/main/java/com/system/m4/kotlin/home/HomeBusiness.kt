package com.system.m4.kotlin.home

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.group.GroupTransactionBusiness
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.SingleResultListener
import com.system.m4.kotlin.paymenttype.PaymentTypeBusiness
import com.system.m4.kotlin.paymenttype.PaymentTypeModel
import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.kotlin.transaction.TransactionBusiness
import com.system.m4.views.vos.GroupTransactionVO
import com.system.m4.views.vos.HomeVO
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TransactionVO
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by enirs on 30/09/2017.
 * Business para Home
 */
class HomeBusiness {

    fun findHomeList(year: Int, month: Int, listener: SingleResultListener<HomeVO>) {

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

    private fun validate(homeDTO: HomeDTO, listener: SingleResultListener<HomeVO>) {

        val listTransaction = homeDTO.listTransaction
        val listTag = homeDTO.listTag
        val listPaymentType = homeDTO.listPaymentType
        val listGroup = homeDTO.listGroup

        if (listTransaction != null && listTag != null && listPaymentType != null && listGroup != null) {

            listTransaction.forEach { TransactionBusiness.fillTransaction(it, listTag, listPaymentType) }

            val homeVO = HomeVO()
            homeVO.tagSummary = TagBusiness.calculateTagSummary(listTransaction)
            homeVO.transactions1Q = listTransaction
            homeVO.pendingTransaction = getPendingTransaction(listTransaction)

            if (!listGroup.isEmpty()) {
                homeVO.group = GroupTransactionBusiness.fillGroupTransaction(listGroup.get(0), listPaymentType)
            }

            listener.onSuccess(homeVO)
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

    abstract class ErrorListener<T>(val listener: SingleResultListener<HomeVO>) : MultResultListener<T> {
        override fun onError(error: String) {
            listener.onError(error)
        }
    }

    fun splitGroupTransaction(homeVO: HomeVO, homeDTO: HomeDTO) {

        val transactions = homeDTO.listTransaction
        val listGroup = homeDTO.listGroup
        val map = hashMapOf<PaymentTypeVO, List<TransactionVO>>()

        if (transactions != null && listGroup != null && listGroup.isNotEmpty()) {

            val paymentTypesGroup = listGroup[0].paymentTypeList // TODO MUDAR ESSA IMPLEMENTACAO NO FIREBASE

            paymentTypesGroup.forEach { type ->
                val mutableList = transactions.filter { it.paymentType.key == type.key }.toMutableList()
                mutableList.sortWith(compareBy({ it.purchaseDate }))
                map.put(type, mutableList)
            }
        }

        homeVO.groupMap = map
    }

    fun splitPendingTransactions(homeVO: HomeVO, homeDTO: HomeDTO) {

        val transactionsDTO = homeDTO.listTransaction
        var transactionsVO = mutableListOf<TransactionVO>()

        if (transactionsDTO != null) {
            transactionsVO = transactionsDTO.filter { it.tag.key.isNullOrBlank() }.toMutableList()
            transactionsVO.sortWith(compareBy({ it.paymentDate }))
        }

        homeVO.pendingTransaction = transactionsVO
    }

    fun splitTransactionsByDate20(homeVO: HomeVO, homeDTO: HomeDTO) {

        homeVO.transactions1Q = mutableListOf()
        homeVO.transactions2Q = mutableListOf()

        homeVO.amount1Q = 0.0
        homeVO.amount2Q = 0.0

        if (homeDTO.listTransaction != null) {

            val transactions = ArrayList(homeDTO.listTransaction)

            if (transactions.isNotEmpty()) {

                val date20 = JavaUtils.DateUtil.getDate(
                        JavaUtils.DateUtil.get(Calendar.YEAR, transactions[0].paymentDate),
                        JavaUtils.DateUtil.get(Calendar.MONTH, transactions[0].paymentDate) + 1, 20)

                transactions.sortWith(compareBy({ it.paymentDate }, { it.tag.parentName }, { it.tag.name }))

                homeVO.transactions1Q = transactions.filter { it.paymentDate.before(date20) }
                homeVO.amount1Q = homeVO.transactions1Q.sumByDouble { it.price }.roundTo2Decimal()

                homeVO.transactions2Q = transactions.filter { it.paymentDate.after(date20) || it.paymentDate.equals(date20) }
                homeVO.amount2Q = homeVO.transactions2Q.sumByDouble { it.price }.roundTo2Decimal()
            }
        }
    }

    fun Double.roundTo2Decimal() = BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
}