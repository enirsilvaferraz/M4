package com.system.m4.kotlin.transaction

/**
 * Created by enirs on 02/10/2017.
 */

import android.text.TextUtils
import com.system.m4.R
import com.system.m4.infrastructure.Constants
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.util.*

/**
 * Created by Enir on 21/04/2017.
 * Presenter
 */

class TransactionManagerPresenter(private val mView: TransactionManagerContract.View) : TransactionManagerContract.Presenter {

    private val REGEX = "^(([1-9][0-9]{0,1})\\/([1-9][0-9]{0,1}))\$"

    private var mVO = TransactionVO()

    override fun setPaymentDate(date: Date?) {
        mVO.paymentDate = date
        mView.setPaymentDate(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)))
    }

    override fun setPurchaseDate(date: Date?) {
        mVO.purchaseDate = date
        mView.setPurchaseDate(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)))
    }

    override fun setPrice(value: Double?) {
        mVO.price = value
        mView.setPrice(JavaUtils.StringUtil.formatEmpty(JavaUtils.NumberUtil.currencyFormat(value)))
    }

    override fun setRefund(value: Double?) {
        mVO.refund = value
        mView.setRefund(JavaUtils.StringUtil.formatEmpty(JavaUtils.NumberUtil.currencyFormat(value)))
    }

    override fun init(transaction: TransactionVO) {
        mVO = transaction
        if (TextUtils.isEmpty(mVO.key)) {
            mVO.paymentDate = Calendar.getInstance().time
        }

        mView.configureModel(transaction)
    }

    override fun setTags(tagVO: TagVO) {
        mVO.tag = tagVO
        mView.setTags(JavaUtils.StringUtil.formatEmpty(tagVO.name))
    }

    override fun setPaymentType(paymentTypeVO: PaymentTypeVO?) {
        if (paymentTypeVO != null) {
            mVO.paymentType = paymentTypeVO
            mView.setPaymentType(JavaUtils.StringUtil.formatEmpty(paymentTypeVO.name))
        }
    }

    override fun setContent(content: String?) {
        mVO.content = content
        mView.setContent(JavaUtils.StringUtil.formatEmpty(content))
    }

    override fun setParcels(parcels: String?) {
        mVO.parcels = parcels
        mView.setParcels(JavaUtils.StringUtil.formatEmpty(parcels))
    }

    override fun requestPriceDialog(text: String) {
        val value = if (text.isEmpty() || text == Constants.EMPTY_FIELD) null else JavaUtils.NumberUtil.removeFormat(text)
        mView.showPriceDialog(value)
    }

    override fun requestRefundDialog(text: String) {
        val value = if (text.isEmpty() || text == Constants.EMPTY_FIELD) null else JavaUtils.NumberUtil.removeFormat(text)
        mView.showRefundDialog(value)
    }

    override fun requestContentDialog(text: String) {
        mView.showContentDialog(if (text == Constants.EMPTY_FIELD) null else text)
    }

    override fun requestParcelsDialog(text: String) {
        mView.showParcelsDialog(if (text == Constants.EMPTY_FIELD) null else text)
    }

    override fun requestPaymentDateDialog(text: String) {

        val date = if (text.isEmpty() || text == Constants.EMPTY_FIELD) {
            Calendar.getInstance().time
        } else {
            JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        }

        mView.showPaymentDateDialog(date)
    }

    override fun requestPurchaseDateDialog(text: String) {

        val date = if (text.isEmpty() || text == Constants.EMPTY_FIELD) {
            Calendar.getInstance().time
        } else {
            JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        }

        mView.showPurchaseDateDialog(date)
    }

    override fun clearContent() {
        mVO.content = null
    }

    override fun clearParcels() {
        mVO.parcels = null
    }

    override fun clearPaymentType() {
        mVO.paymentType = null
    }

    override fun clearPrice() {
        mVO.price = null
    }

    override fun clearRefund() {
        mVO.refund = null
    }

    override fun clearPurchaseDate() {
        mVO.purchaseDate = null
    }

    override fun clearPaymentDate() {
        mVO.paymentDate = null
    }

    override fun save() {

        if (JavaUtils.ClassUtil.isEmpty(mVO.tag)) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_tag)
        } else if (JavaUtils.ClassUtil.isEmpty(mVO.paymentType)) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_type)
        } else if (mVO.paymentDate == null) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_date)
        } else if (mVO.price == null) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_price)
        } else if (mVO.parcels.isNotEmpty() && !mVO.parcels.contains(Regex(REGEX))) {
            mView.showError(R.string.system_error_not_allowed_field, R.string.transaction_parcels)
        } else {

            TransactionBusiness.save(mVO, object : PersistenceListener<TransactionVO> {

                override fun onSuccess(model: TransactionVO) {
                    mView.dismissDialog(model)
                }

                override fun onError(error: String) {
                    mView.showError(error)
                }
            })
        }
    }
}