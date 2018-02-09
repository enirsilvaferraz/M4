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

    /*
     * INIT
     */

    override fun init(transaction: TransactionVO) {

        mVO = transaction
        if (TextUtils.isEmpty(mVO.key)) {
            mVO.paymentDate = Calendar.getInstance().time
        }

        mView.setToolbarTitle(transaction.tag.name)
        mView.setTags(getValueOrEmpty(transaction.tag.name))
        mView.setPaymentDate(getValueOrEmpty(transaction.paymentDate))
        mView.setPurchaseDate(getValueOrEmpty(transaction.purchaseDate))
        mView.setPrice(getValueOrEmpty(transaction.price))
        mView.setRefund(getValueOrEmpty(transaction.refund))
        mView.setPaymentType(getValueOrEmpty(if (transaction.paymentType != null) transaction.paymentType.name else null))
        mView.setContent(getValueOrEmpty(transaction.content))
        mView.setParcels(getValueOrEmpty(transaction.parcels))
        mView.setAlreadyPaid(if (transaction.alreadyPaid) "YES" else "NO")
    }

    /*
     * ON CLICK EVENTS
     */

    override fun onPurchaseDateClick(text: String) {

        val date = if (text.isEmpty() || text == Constants.EMPTY_FIELD) {
            Calendar.getInstance().time
        } else {
            JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        }

        mView.showPurchaseDateDialog(date)
    }

    override fun onPaymentDateClick(text: String) {

        val date = if (text.isEmpty() || text == Constants.EMPTY_FIELD) {
            Calendar.getInstance().time
        } else {
            JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        }

        mView.showPaymentDateDialog(date)
    }

    override fun onPriceClick(text: String) {
        val value = if (text.isEmpty() || text == Constants.EMPTY_FIELD) null else JavaUtils.NumberUtil.removeFormat(text)
        mView.showPriceDialog(value)
    }

    override fun onRefundClick(text: String) {
        val value = if (text.isEmpty() || text == Constants.EMPTY_FIELD) null else JavaUtils.NumberUtil.removeFormat(text)
        mView.showRefundDialog(value)
    }

    override fun onContentClick(text: String) {
        mView.showContentDialog(if (text == Constants.EMPTY_FIELD) null else text)
    }

    override fun onParcelsClick(text: String) {
        mView.showParcelsDialog(if (text == Constants.EMPTY_FIELD) null else text)
    }

    override fun onAlreadyPaidClick(value: String) {
        mVO.alreadyPaid = !value.equals("YES")
        mView.setAlreadyPaid(if (mVO.alreadyPaid) "YES" else "NO")
    }

    /*
     * ON CLICK EVENTS
     */

    override fun onPaymentTypeLongClick(): Boolean {
        setPaymentType(null)
        return true
    }

    override fun onPurchaseDateLongClick(): Boolean {
        setPurchaseDate(null)
        return true
    }

    override fun onPaymentDateLongClick(): Boolean {
        setPaymentDate(null)
        return true
    }

    override fun onPriceLongClick(): Boolean {
        setPrice(null)
        return true
    }

    override fun onRefundLongClick(): Boolean {
        setRefund(null)
        return true
    }

    override fun onContentLongClick(): Boolean {
        setContent(null)
        return true
    }

    override fun onParcelsLongClick(): Boolean {
        setParcels(null)
        return true
    }

    /*
     * SETTERS
     */

    override fun setTags(tagVO: TagVO) {
        mVO.tag = tagVO
        mView.setTags(getValueOrEmpty(tagVO.name))
    }

    override fun setPaymentType(paymentTypeVO: PaymentTypeVO?) {
        mVO.paymentType = paymentTypeVO
        mView.setPaymentType(getValueOrEmpty(paymentTypeVO?.name))
    }

    override fun setPaymentDate(date: Date?) {
        mVO.paymentDate = date
        mView.setPaymentDate(getValueOrEmpty(date))
    }

    override fun setPurchaseDate(date: Date?) {
        mVO.purchaseDate = date
        mView.setPurchaseDate(getValueOrEmpty(date))
    }

    override fun setPrice(value: Double?) {
        mVO.price = value
        mView.setPrice(getValueOrEmpty(value))
    }

    override fun setRefund(value: Double?) {
        mVO.refund = value
        mView.setRefund(getValueOrEmpty(value))
    }

    override fun setContent(content: String?) {
        mVO.content = content
        mView.setContent(getValueOrEmpty(content))
    }

    override fun setParcels(parcels: String?) {
        mVO.parcels = parcels
        mView.setParcels(getValueOrEmpty(parcels))
    }

    override fun setAlreadyPaid(alreadyPaid: Boolean) {
        mVO.alreadyPaid = alreadyPaid
        mView.setAlreadyPaid(if (mVO.alreadyPaid) "YES" else "NO")
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
        } else if (mVO.parcels != null && mVO.parcels.isNotEmpty() && !mVO.parcels.contains(Regex(REGEX))) {
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

    private fun getValueOrEmpty(value: Any?): String = when {
        value == null -> mView.getContext().getString(R.string.system_empty_field)
        value is String && value.isNotEmpty() -> value
        value is Date -> JavaUtils.DateUtil.format(value, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)
        value is Double -> JavaUtils.NumberUtil.currencyFormat(value)
        else -> mView.getContext().getString(R.string.system_empty_field)
    }
}