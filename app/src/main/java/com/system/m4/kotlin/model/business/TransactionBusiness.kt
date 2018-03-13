package com.system.m4.kotlin.model.business

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.data.TransactionRepository
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.model.entity.TransactionModel
import com.system.m4.labs.vos.PaymentTypeVO
import com.system.m4.labs.vos.TagVO
import com.system.m4.labs.vos.TransactionVO
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by enirs on 30/09/2017.
 * Business para o gerenciamento de transacoes
 */
class TransactionBusiness @Inject constructor() {

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

                while (actualParcel <= countParcel) {

                    year = JavaUtils.DateUtil.get(Calendar.YEAR, vo.paymentDate)
                    month = JavaUtils.DateUtil.get(Calendar.MONTH, vo.paymentDate)

                    vo.parcels = "${actualParcel}/${countParcel}"

                    TransactionRepository(year, month).save(fromTransaction(vo), listener)

                    val instance = Calendar.getInstance()
                    instance.time = vo.paymentDate
                    instance.add(Calendar.MONTH, 1)
                    vo.paymentDate = instance.time

                    actualParcel++
                }

            } else {
                TransactionRepository(year, month).save(fromTransaction(vo), listener)
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

        TransactionRepository(year, month).findAll(listener = object : MultResultListener<TransactionModel> {

            override fun onSuccess(list: ArrayList<TransactionModel>) {
                listener.onSuccess(fromTransaction(list))
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
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
        dto.parcels = vo.parcels
        dto.alreadyPaid = vo.isAlreadyPaid
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
        vo.refund = if (dto.refund != null) dto.refund else 0.0
        vo.isApproved = true
        vo.parcels = dto.parcels
        vo.isAlreadyPaid = dto.alreadyPaid

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

}