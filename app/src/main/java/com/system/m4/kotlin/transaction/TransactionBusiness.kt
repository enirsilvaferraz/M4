package com.system.m4.kotlin.transaction

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by enirs on 30/09/2017.
 * Business para o gerenciamento de transacoes
 */
class TransactionBusiness {

    companion object {

        fun save(vo: TransactionVO, persistListener: PersistenceListener<TransactionVO>?) {

            var year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
            var month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

            val listener = object : PersistenceListener<TransactionModel> {

                override fun onSuccess(model: TransactionModel) {
                    persistListener?.onSuccess(fromTransaction(model))
                }

                override fun onError(error: String) {
                    persistListener?.onError(error)
                }
            }

            if (vo.key == null) {

                if (!vo.parcels.isNullOrBlank()) {

                    var actualParcel = vo.parcels.split("/")[0].toInt()
                    val countParcel = vo.parcels.split("/")[1].toInt()

                    do {

                        val instance = Calendar.getInstance()
                        instance.time = vo.paymentDate
                        instance.set(Calendar.MONTH, actualParcel)
                        vo.paymentDate = instance.time

                        year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
                        month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

                        vo.parcels = "${actualParcel}/${countParcel}"

                        TransactionRepository(year, month).create(fromTransaction(vo), listener)
                        actualParcel++

                    } while (actualParcel <= countParcel)

                } else {
                    TransactionRepository(year, month).create(fromTransaction(vo), listener)
                }

            } else if (isInPath(vo)) {
                TransactionRepository(year, month).update(fromTransaction(vo), listener)

            } else {

                val yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDateOrigin)
                val monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDateOrigin)

                TransactionRepository(yearOrigin, monthOrigin).delete(fromTransaction(vo), object : PersistenceListener<TransactionModel> {

                    override fun onSuccess(model: TransactionModel) {
                        TransactionRepository(year, month).update(model, listener)
                    }

                    override fun onError(error: String) {
                        persistListener?.onError(error)
                    }
                })
            }
        }

        fun delete(vo: TransactionVO, listener: PersistenceListener<TransactionModel>) {

            val dto = fromTransaction(vo)

            val year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
            val month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

            TransactionRepository(year, month).delete(dto, object : PersistenceListener<TransactionModel> {

                override fun onSuccess(model: TransactionModel) {
                    listener.onSuccess(model)
                }

                override fun onError(error: String) {
                    listener.onError(error)
                }
            })
        }

        private fun isInPath(vo: TransactionVO): Boolean {

            val year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
            val month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

            val yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDateOrigin)
            val monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDateOrigin)

            return year == yearOrigin && month == monthOrigin
        }

        fun findAll(year: Int, month: Int, listener: MultResultListener<TransactionVO>) {

            TransactionRepository(year, month).findAll(object : MultResultListener<TransactionModel> {

                override fun onSuccess(list: ArrayList<TransactionModel>) {

                    val transactions = fromTransaction(list)

                    val calendar = Calendar.getInstance()
                    if (year > calendar.get(Calendar.YEAR) || (year == calendar.get(Calendar.YEAR) && month >= calendar.get(Calendar.MONTH))) {
                        findFixed(year, month, transactions, listener)
                    } else {
                        listener.onSuccess(transactions)
                    }
                }

                override fun onError(error: String) {
                    listener.onError(error)
                }
            })
        }

        private fun findFixed(year: Int, month: Int, transactions: ArrayList<TransactionVO>, listener: MultResultListener<TransactionVO>) {

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.add(Calendar.MONTH, -1)

            TransactionRepository(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)).findFixed(object : MultResultListener<TransactionModel> {
                override fun onSuccess(list: ArrayList<TransactionModel>) {

                    val fixedTransactions = fromFixedTransaction(list, year, month)

                    for (transaction in transactions) {
                        for (fixedTransaction in fixedTransactions) {
                            if (fixedTransaction.key.equals(transaction.key)) {
                                transaction.isFixed = true
                            }
                        }
                    }

                    transactions.addAll(fixedTransactions.filter { shouldRetain(it) })
                    listener.onSuccess(transactions)
                }

                override fun onError(error: String) {
                    listener.onError(error)
                }

                private fun shouldRetain(it: TransactionVO): Boolean {
                    for (transaction in transactions) {
                        if (transaction.key.equals(it.key)) {
                            return false
                        }
                    }
                    return true
                }
            })
        }

        fun pin(vo: TransactionVO, listener: PersistenceListener<TransactionVO>) {
            vo.isFixed = true
            save(vo, listener)
        }

        fun unpin(vo: TransactionVO, listener: PersistenceListener<TransactionVO>) {
            vo.isFixed = false
            save(vo, listener)
        }

        fun fromTransaction(vo: TransactionVO): TransactionModel {
            val dto = TransactionModel()
            dto.key = vo.key
            dto.tag = vo.tag.key
            dto.paymentType = vo.paymentType.key
            dto.paymentDate = JavaUtils.DateUtil.format(vo.paymentDate, JavaUtils.DateUtil.YYYY_MM_DD)
            dto.purchaseDate = if (vo.purchaseDate != null) JavaUtils.DateUtil.format(vo.purchaseDate, JavaUtils.DateUtil.YYYY_MM_DD) else null
            dto.content = vo.content
            dto.price = vo.price
            dto.refund = vo.refund
            dto.fixed = vo.isFixed
            dto.parcels = vo.parcels
            return dto
        }

        fun fromTransaction(dto: TransactionModel): TransactionVO {
            val vo = TransactionVO()
            vo.key = dto.key
            vo.tag = TagVO()
            vo.tag.key = dto.tag
            vo.paymentType = PaymentTypeVO()
            vo.paymentType.key = dto.paymentType
            vo.paymentDate = JavaUtils.DateUtil.parse(dto.paymentDate, JavaUtils.DateUtil.YYYY_MM_DD)
            vo.purchaseDate = if (dto.purchaseDate != null) JavaUtils.DateUtil.parse(dto.purchaseDate, JavaUtils.DateUtil.YYYY_MM_DD) else null
            vo.content = dto.content
            vo.price = dto.price
            vo.refund = dto.refund
            vo.isApproved = true
            vo.isFixed = dto.fixed
            vo.parcels = dto.parcels

            // Usado para saber onde é o path, não é armazenado no Firebase
            vo.paymentDateOrigin = vo.paymentDate
            return vo
        }

        fun fromFixedTransaction(dto: TransactionModel, year: Int, month: Int): TransactionVO {

            val vo = TransactionVO()
            vo.key = dto.key
            vo.tag = TagVO()
            vo.tag.key = dto.tag
            vo.paymentType = PaymentTypeVO()
            vo.paymentType.key = dto.paymentType

            val calPaymentDate = Calendar.getInstance()
            calPaymentDate.time = JavaUtils.DateUtil.parse(dto.paymentDate, JavaUtils.DateUtil.YYYY_MM_DD)
            calPaymentDate.set(Calendar.YEAR, year)
            calPaymentDate.set(Calendar.MONTH, month)
            vo.paymentDate = calPaymentDate.time

            if (dto.purchaseDate != null) {
                val calPurchaseDate = Calendar.getInstance()
                calPurchaseDate.time = JavaUtils.DateUtil.parse(dto.purchaseDate, JavaUtils.DateUtil.YYYY_MM_DD)
                calPurchaseDate.set(Calendar.YEAR, year)
                calPurchaseDate.set(Calendar.MONTH, month)
                vo.purchaseDate = calPurchaseDate.time
            }

            vo.content = dto.content
            vo.price = dto.price
            vo.refund = dto.refund

            vo.isApproved = false
            vo.isFixed = dto.fixed

            // Usado para saber onde é o path, não é armazenado no Firebase
            vo.paymentDateOrigin = vo.paymentDate
            return vo
        }

        fun fromFixedTransaction(list: List<TransactionModel>, year: Int, month: Int): ArrayList<TransactionVO> {
            val listVO = ArrayList<TransactionVO>()
            for (model in list) {
                listVO.add(fromFixedTransaction(model, year, month))
            }
            return listVO
        }

        fun fromTransaction(list: List<TransactionModel>): ArrayList<TransactionVO> {
            val listVO = ArrayList<TransactionVO>()
            for (model in list) {
                listVO.add(fromTransaction(model))
            }
            return listVO
        }

        fun fillTransaction(vo: TransactionVO, tags: List<TagVO>, paymentTypes: List<PaymentTypeVO>?): TransactionVO {

            val index = tags.indexOf(vo.tag)
            if (index > -1) {
                vo.tag = tags[index]
            } else {
                val tag = TagVO()
                tag.name = "(Pendente de Avaliação)"
                vo.tag = tag
            }

            if (paymentTypes != null) {
                vo.paymentType = paymentTypes[paymentTypes.indexOf(vo.paymentType)]
            }
            return vo
        }

        fun fillTransaction(vos: ArrayList<TransactionVO>, tags: List<TagVO>, paymentTypes: List<PaymentTypeVO>?): ArrayList<TransactionVO> {
            for (vo in vos) {
                fillTransaction(vo, tags, paymentTypes)
            }
            return vos
        }
    }
}