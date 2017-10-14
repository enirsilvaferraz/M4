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

    private var mVO: TransactionVO

    init {
        this.mVO = TransactionVO()
    }

    override fun setPaymentDate(date: Date?) {
        mVO.paymentDate = date
        mView.setPaymentDate(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)))
    }

    override fun setPurchaseDate(date: Date?) {
        mVO.purchaseDate = date
        mView.setPurchaseDate(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)))
    }

    override fun setValue(value: Double?) {
        mVO.price = value
        mView.setValue(JavaUtils.StringUtil.formatEmpty(JavaUtils.NumberUtil.currencyFormat(value)))
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

    override fun requestValueDialog(text: String) {
        val value = if (text.isEmpty() || text == Constants.EMPTY_FIELD) null else JavaUtils.NumberUtil.removeFormat(text)
        mView.showValueDialog(value)
    }

    override fun requestContentDialog(text: String) {
        mView.showContentDialog(if (text == Constants.EMPTY_FIELD) null else text)
    }

    override fun requestPaymentDateDialog(text: String) {

        val date: Date
        if (text.isEmpty() || text == Constants.EMPTY_FIELD) {
            date = Calendar.getInstance().time
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        }

        mView.showPaymentDateDialog(date)
    }

    override fun requestPurchaseDateDialog(text: String) {

        val date: Date
        if (text.isEmpty() || text == Constants.EMPTY_FIELD) {
            date = Calendar.getInstance().time
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        }

        mView.showPurchaseDateDialog(date)
    }

    override fun clearContent() {
        mVO.content = null
    }

    override fun clearPaymentType() {
        mVO.paymentType = null
    }

    override fun clearPrice() {
        mVO.price = null
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
        } else {

            TransactionBusiness().save(mVO, object : PersistenceListener<TransactionModel> {

                override fun onSuccess(dto: TransactionModel) {
                    mView.dismissDialog(TransactionBusiness.fromTransaction(dto))
                }

                override fun onError(error: String) {
                    mView.showError(error)
                }
            })
        }
    }
}