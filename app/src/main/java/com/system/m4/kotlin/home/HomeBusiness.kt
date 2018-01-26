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
import com.system.m4.views.vos.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by enirs on 30/09/2017.
 * Business para Home
 */
class HomeBusiness {

    fun findHomeList(year: Int, month: Int, listener: SingleResultListener<HomeVO>) {

        val homeDTO = HomeDTO(year, month)

        TransactionBusiness.findAll(year, month, object : ErrorListener<TransactionVO>(listener) {
            override fun onSuccess(list: ArrayList<TransactionVO>) {
                homeDTO.listTransaction = list
                validate(homeDTO, listener)
            }
        })

        TagBusiness.findAll(object : ErrorListener<TagModel>(listener) {
            override fun onSuccess(list: ArrayList<TagModel>) {
                homeDTO.listTag = TagBusiness.fromTag(list)
                validate(homeDTO, listener)
            }
        })

        PaymentTypeBusiness.findAll(object : ErrorListener<PaymentTypeModel>(listener) {
            override fun onSuccess(list: ArrayList<PaymentTypeModel>) {
                homeDTO.listPaymentType = PaymentTypeBusiness.fromPaymentType(list)
                validate(homeDTO, listener)
            }
        })

        GroupTransactionBusiness.findAll(object : ErrorListener<GroupTransactionVO>(listener) {
            override fun onSuccess(list: ArrayList<GroupTransactionVO>) {
                homeDTO.listGroup = list
                validate(homeDTO, listener)
            }
        })
    }

    private fun validate(homeDTO: HomeDTO, listener: SingleResultListener<HomeVO>) {

        if (homeDTO.listTransaction != null && homeDTO.listTag != null && homeDTO.listPaymentType != null && homeDTO.listGroup != null) {

            homeDTO.listTransaction!!.forEach { TransactionBusiness.fillTransaction(it, homeDTO.listTag!!, homeDTO.listPaymentType!!) }

            val homeVO = HomeVO()
            homeVO.tagSummary = splitTagSummary(homeDTO)
            homeVO.transactions1Q = splitTransactionsByDate20(homeDTO, true)
            homeVO.transactions2Q = splitTransactionsByDate20(homeDTO, false)
            homeVO.pendingTransaction = splitPendingTransactions(homeDTO)

//            if (homeDTO.listGroup!!.isNotEmpty()) {
//                homeVO.group = GroupTransactionBusiness.fillGroupTransaction(homeDTO.listGroup!!.get(0), homeDTO.listPaymentType!!)
//            }

            listener.onSuccess(homeVO)
        }
    }

    abstract class ErrorListener<T>(val listener: SingleResultListener<HomeVO>) : MultResultListener<T> {
        override fun onError(error: String) {
            listener.onError(error)
        }
    }

    fun splitTagSummary(homeDTO: HomeDTO): List<TagSummaryVO> {

        val itens = arrayListOf<TagSummaryVO>()

        if (homeDTO.listTag != null && homeDTO.listTag!!.isNotEmpty()) {

            homeDTO.listTag!!.forEach { tag ->
                val filter = homeDTO.listTransaction?.filter { transaction -> transaction.tag.key == tag.key }
                if (filter!!.isNotEmpty()) itens.add(TagSummaryVO(tag.key, tag.parentName, tag.name, filter.sumByDouble { it.price }))
            }

            itens.sortWith(compareBy({ it.parentName }, { it.name }, { it.value }))
        }

        return itens
    }

    fun splitGroupTransaction(homeDTO: HomeDTO): HashMap<PaymentTypeVO, TransactionListVO> {

        val transactionsDTO = homeDTO.listTransaction
        val groupsDTO = homeDTO.listGroup

        val map = hashMapOf<PaymentTypeVO, TransactionListVO>()

        if (transactionsDTO != null && groupsDTO != null && groupsDTO.isNotEmpty()) {

            // TODO MUDAR ESSA IMPLEMENTACAO NO FIREBASE
            groupsDTO[0].paymentTypeList.forEach { type ->

                val transactions = transactionsDTO.filter { it.paymentType.key == type.key }.toMutableList()
                transactions.sortWith(compareBy({ it.purchaseDate }))

                val amount = transactions.sumByDouble { it.price }.roundTo2Decimal()

                val transactionListVO = TransactionListVO(transactions, amount)
                map.put(type, transactionListVO)
            }
        }

        return map
    }

    fun splitPendingTransactions(homeDTO: HomeDTO): MutableList<TransactionVO> {

        val transactionsDTO = homeDTO.listTransaction
        var transactions = mutableListOf<TransactionVO>()

        if (transactionsDTO != null && transactionsDTO.isNotEmpty()) {
            transactions = transactionsDTO.filter { it.tag.key.isNullOrBlank() }.toMutableList()
            transactions.sortWith(compareBy({ it.paymentDate }))
        }

        return transactions
    }

    fun splitTransactionsByDate20(homeDTO: HomeDTO, firstFortnight: Boolean): TransactionListVO {

        val transactionListVO = TransactionListVO(mutableListOf(), 0.0)

        if (homeDTO.listTransaction != null) {
            val transactions = ArrayList(homeDTO.listTransaction)

            if (transactions.isNotEmpty()) {

                val date20 = JavaUtils.DateUtil.getDate(
                        JavaUtils.DateUtil.get(Calendar.YEAR, transactions[0].paymentDate),
                        JavaUtils.DateUtil.get(Calendar.MONTH, transactions[0].paymentDate) + 1, 20)

                transactions.sortWith(compareBy({ it.paymentDate }, { it.tag.parentName }, { it.tag.name }))

                transactionListVO.transactions = transactions.filter {
                    if (firstFortnight) it.paymentDate.before(date20) else it.paymentDate.equals(date20) || it.paymentDate.after(date20)
                }

                transactionListVO.amount = transactionListVO.transactions.sumByDouble { it.price }.roundTo2Decimal()
            }
        }

        return transactionListVO
    }

    fun Double.roundTo2Decimal() = BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
}