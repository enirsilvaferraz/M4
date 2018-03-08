package com.system.m4.kotlin.paymenttype.list

import com.system.m4.kotlin.paymenttype.PaymentTypeModel
import com.system.m4.kotlin.paymenttype.manager.PaymentTypeManagerContract
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface PaymentTypeListContract {

    interface View {
        fun loadList(list: ArrayList<PaymentTypeModel>)
        fun selectItem(model: PaymentTypeModel)
        fun remove(model: PaymentTypeModel)
        fun addOrUpdateItem(model: PaymentTypeModel)
        fun openManager(model: PaymentTypeModel?)
        fun showLoading()
        fun stopLoading()
        fun showError(error: String)
        fun dismiss()
    }

    interface Presenter : PaymentTypeListContract.OnAdapterClickListener, PaymentTypeManagerContract.OnCompleteListener {
        fun onInit()
        fun onAddNewClicked()
    }

    interface OnSelectedListener {
        fun onSelect(model: PaymentTypeModel)
    }

    interface OnAdapterClickListener {
        fun onSelectClicked(model: PaymentTypeModel): Boolean
        fun onEditClicked(model: PaymentTypeModel): Boolean
        fun onDeleteClicked(model: PaymentTypeModel): Boolean
    }
}