package com.system.m4.kotlin.transaction

import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import com.system.m4.views.vos.VOInterface
import java.util.*

/**
 * Created by Enir on 21/04/2017.
 * Contrato para TransactionManager
 */

interface TransactionManagerContract {

    interface View {

        fun configureModel(transaction: TransactionVO)

        fun setPaymentDate(value: String)

        fun setPurchaseDate(value: String)

        fun setValue(value: String)

        fun setTags(value: String)

        fun setPaymentType(value: String)

        fun setContent(value: String)

        fun showValueDialog(value: Double?)

        fun showContentDialog(value: String?)

        fun showPaymentDateDialog(date: Date)

        fun showPurchaseDateDialog(date: Date)

        fun dismissDialog(vo: VOInterface<*>)

        fun showError(error: String)

        fun showSuccessMessage(template: Int, param: Int)

        fun showError(template: Int, param: Int)
    }

    interface Presenter {

        fun setPaymentDate(date: Date?)

        fun setPurchaseDate(date: Date?)

        fun setValue(value: Double?)

        fun setTags(tagVO: TagVO)

        fun setPaymentType(paymentTypeVO: PaymentTypeVO?)

        fun setContent(content: String?)

        fun requestValueDialog(text: String)

        fun requestContentDialog(text: String)

        fun requestPaymentDateDialog(text: String)

        fun requestPurchaseDateDialog(text: String)

        fun clearContent()

        fun clearPaymentType()

        fun clearPrice()

        fun clearPurchaseDate()

        fun clearPaymentDate()

        fun save()

        fun init(transaction: TransactionVO)
    }
}