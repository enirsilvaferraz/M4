package com.system.m4.kotlin.transaction

import com.system.m4.infrastructure.ConverterUtils
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.views.vos.Transaction
import java.util.*

/**
 * Created by enirs on 30/09/2017.
 * Business para o gerenciamento de transacoes
 */
class TransactionBusiness {

    fun save(vo: Transaction, persistListener: PersistenceListener<TransactionModel>?) {

        val listener = object : PersistenceListener<TransactionModel> {

            override fun onSuccess(model: TransactionModel) {
                persistListener?.onSuccess(model)
            }

            override fun onError(error: String) {
                persistListener?.onError(error)
            }
        }

        val year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
        val month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

        val model = ConverterUtils.fromTransaction(vo)

        if (model.key == null) {
            TransactionRepository(year, month).create(model, listener)

        } else if (isInPath(vo)) {
            TransactionRepository(year, month).update(model, listener)

        } else {

            val yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDateOrigin)
            val monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDateOrigin)

            TransactionRepository(yearOrigin, monthOrigin).delete(model, object : PersistenceListener<TransactionModel> {

                override fun onSuccess(model: TransactionModel) {
                    TransactionRepository(year, month).update(model, listener)
                }

                override fun onError(error: String) {
                    persistListener?.onError(error)
                }
            })
        }
    }

    fun delete(vo: Transaction, listener: PersistenceListener<TransactionModel>) {

        val dto = ConverterUtils.fromTransaction(vo)

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

    private fun isInPath(vo: Transaction): Boolean {

        val year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
        val month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

        val yearOrigin = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDateOrigin)
        val monthOrigin = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDateOrigin)

        return year == yearOrigin && month == monthOrigin
    }

    fun findAll(year: Int, month: Int, listener: MultResultListener<Transaction>) {

        TransactionRepository(year, month).findAll(object : MultResultListener<TransactionModel> {
            override fun onSuccess(list: ArrayList<TransactionModel>) {
                listener.onSuccess(ConverterUtils.fromTransaction(list))
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}