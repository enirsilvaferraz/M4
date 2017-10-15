package com.system.m4.kotlin.transaction

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.util.*

/**
 * Created by enirs on 30/09/2017.
 * Business para o gerenciamento de transacoes
 */
class TransactionBusiness {

    fun save(vo: TransactionVO, persistListener: PersistenceListener<TransactionModel>?) {

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

        val model = fromTransaction(vo)

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
                listener.onSuccess(fromTransaction(list))
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }

    companion object {

        fun fromTransaction(vo: TransactionVO): TransactionModel {
            val dto = TransactionModel()
            dto.key = vo.key
            dto.tag = vo.tag.key
            dto.paymentType = vo.paymentType.key
            dto.paymentDate = JavaUtils.DateUtil.format(vo.paymentDate, JavaUtils.DateUtil.YYYY_MM_DD)
            dto.purchaseDate = if (vo.purchaseDate != null) JavaUtils.DateUtil.format(vo.purchaseDate, JavaUtils.DateUtil.YYYY_MM_DD) else null
            dto.content = vo.content
            dto.price = vo.price
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

            // Usado para saber onde é o path, não é armazenado no Firebase
            vo.paymentDateOrigin = vo.paymentDate
            return vo
        }

        fun fromTransaction(list: List<TransactionModel>): ArrayList<TransactionVO> {
            val listVO = ArrayList<TransactionVO>()
            for (model in list) {
                listVO.add(fromTransaction(model))
            }
            return listVO
        }

        fun fillTransaction(vo: TransactionVO, tags: List<TagVO>, paymentTypes: List<PaymentTypeVO>): TransactionVO {
            val index = tags.indexOf(vo.tag)
            if (index > -1) {
                vo.tag = tags[index]
            } else {
                val tag = TagVO()
                tag.name = "(Pendente de Avaliação)"
                vo.tag = tag
            }
            vo.paymentType = paymentTypes[paymentTypes.indexOf(vo.paymentType)]
            return vo
        }
    }
}