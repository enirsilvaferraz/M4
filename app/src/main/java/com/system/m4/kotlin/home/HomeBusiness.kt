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

            var group: GroupTransactionVO? = null
            if (homeDTO.listGroup!!.isNotEmpty()) {
                group = GroupTransactionBusiness.fillGroupTransaction(homeDTO.listGroup!![0], homeDTO.listPaymentType!!) // TODO MUDAR A IMPLEMENTACAO DE GROUPOS NO FIREBASE
            }

            val homeVO = HomeVO()
            homeVO.tagSummary = splitTagSummary(homeDTO.listTag!!, homeDTO.listTransaction!!)
            homeVO.transactions1Q = splitTransactionsByDate20(homeDTO.listTransaction!!, true, group)
            homeVO.transactions2Q = splitTransactionsByDate20(homeDTO.listTransaction!!, false, group)
            homeVO.pendingTransaction = splitPendingTransactions(homeDTO.listTransaction!!)
            homeVO.groups = splitGroupTransaction(group, homeDTO.listTransaction!!)
            homeVO.amount = homeVO.transactions1Q.amount + homeVO.transactions2Q.amount
            homeVO.refound = homeVO.transactions1Q.refound + homeVO.transactions2Q.refound

            listener.onSuccess(homeVO)
        }
    }

    abstract class ErrorListener<T>(val listener: SingleResultListener<HomeVO>) : MultResultListener<T> {
        override fun onError(error: String) {
            listener.onError(error)
        }
    }

    fun splitTagSummary(tags: MutableList<TagVO>, transactions: MutableList<TransactionVO>): List<TagSummaryVO> {

        val items = arrayListOf<TagSummaryVO>()

        tags.forEach { tag ->
            val filter = transactions.filter { transaction -> transaction.tag.key == tag.key }
            if (filter.isNotEmpty())
                items.add(TagSummaryVO(tag.key, tag.parentName, tag.name, filter.sumByDouble { it.price }))
        }

        items.sortWith(compareBy({ it.parentName }, { it.name }, { it.value }))
        return items
    }

    fun splitGroupTransaction(group: GroupTransactionVO?, transactions: MutableList<TransactionVO>): HashMap<PaymentTypeVO, TransactionListVO> {

        val map = hashMapOf<PaymentTypeVO, TransactionListVO>()

        if (group != null) group.paymentTypeList.forEach { type ->

            val items = transactions.filter { it.paymentType.key == type.key }.map {
                it.isOnGroup = true
                it
            }.toMutableList()

            if (items.isNotEmpty()) {

                items.sortWith(compareBy({ it.purchaseDate }))

                val transactionListVO = TransactionListVO(items, items.sumByDouble { it.price }.roundTo2Decimal(), items.sumByDouble { it.refund }.roundTo2Decimal())
                map.put(type, transactionListVO)
            }
        }

        return map
    }

    fun splitPendingTransactions(transactions: MutableList<TransactionVO>): MutableList<TransactionVO> {
        val items = transactions.filter { it.tag.key.isNullOrBlank() }.toMutableList()
        items.sortWith(compareBy({ it.paymentDate }))
        return items
    }

    fun splitTransactionsByDate20(transactions: MutableList<TransactionVO>, firstFortnight: Boolean, group: GroupTransactionVO?): TransactionListVO {

        if (transactions.isNotEmpty()) {

            val date20 = JavaUtils.DateUtil.getDate(transactions[0].paymentDate, 20)

            val transactions20 = transactions.filter {
                if (firstFortnight) it.paymentDate.before(date20) else it.paymentDate.equals(date20) || it.paymentDate.after(date20)
            }.toMutableList()

            splitTransactionGrouped(group, transactions20)

            transactions20.sortWith(compareBy({ it.paymentDate }, { it.tag.parentName }, { it.tag.name }))
            return TransactionListVO(transactions20, transactions20.sumByDouble { it.price }.roundTo2Decimal(), transactions20.sumByDouble { it.refund }.roundTo2Decimal())

        } else {
            return TransactionListVO(mutableListOf(), 0.0, 0.0)
        }
    }

    fun splitTransactionGrouped(group: GroupTransactionVO?, transactions: MutableList<TransactionVO>): MutableList<TransactionVO> {

        if (group != null) group.paymentTypeList.forEach { paymentType ->

            val filter = transactions.filter { it.paymentType.key == paymentType.key }

            if (filter.isNotEmpty()) {

                val transaction = TransactionVO()
                transaction.paymentType = paymentType
                transaction.tag = TagVO(null, "Grupo de Transações", paymentType.name)
                transaction.paymentDate = filter[0].paymentDate
                transaction.price = filter.sumByDouble { it.price }
                transaction.refund = filter.sumByDouble { it.refund }
                transaction.isClickable = false

                transactions.removeAll { it.paymentType.key == paymentType.key }
                transactions.add(transaction)
            }
        }

        return transactions
    }

    fun Double.roundTo2Decimal() = BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
}