package com.system.m4.kotlin.transaction

import android.content.Context
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

        fun setToolbarTitle(titleString: String)
        fun setPaymentDate(value: String)
        fun setPurchaseDate(value: String)
        fun setPrice(value: String)
        fun setRefund(value: String)
        fun setTags(value: String)
        fun setPaymentType(value: String)
        fun setContent(value: String)
        fun setParcels(value: String)
        fun setAlreadyPaid(value: String)

        fun showPriceDialog(value: Double?)
        fun showRefundDialog(value: Double?)
        fun showContentDialog(value: String?)
        fun showParcelsDialog(value: String?)
        fun showPaymentDateDialog(date: Date)

        fun showPurchaseDateDialog(date: Date)
        fun dismissDialog(vo: VOInterface<*>)
        fun showSuccessMessage(template: Int, param: Int)
        fun showError(error: String)
        fun showError(template: Int, param: Int)
        fun getContext(): Context
    }

    interface Presenter {

        /*
         * INIT
         */

        fun init(transaction: TransactionVO)

        /*
         * ON CLICK EVENTS
         */

        fun onPurchaseDateClick(text: String)
        fun onPaymentDateClick(text: String)
        fun onPriceClick(text: String)
        fun onRefundClick(text: String)
        fun onContentClick(text: String)
        fun onParcelsClick(text: String)
        fun onAlreadyPaidClick(value: String)

        /*
         * ON CLICK EVENTS
         */

        fun onPaymentTypeLongClick(): Boolean
        fun onPurchaseDateLongClick(): Boolean
        fun onPaymentDateLongClick(): Boolean
        fun onPriceLongClick(): Boolean
        fun onRefundLongClick(): Boolean
        fun onContentLongClick(): Boolean
        fun onParcelsLongClick(): Boolean

        /*
         * SETTERS
         */

        fun setTags(tagVO: TagVO)
        fun setPaymentType(paymentTypeVO: PaymentTypeVO?)
        fun setPurchaseDate(date: Date?)
        fun setPaymentDate(date: Date?)
        fun setPrice(value: Double?)
        fun setRefund(value: Double?)
        fun setContent(content: String?)
        fun setParcels(parcels: String?)
        fun setAlreadyPaid(alreadyPaid: Boolean)

        fun save()
    }
}