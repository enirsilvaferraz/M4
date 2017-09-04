package com.system.m4.kotlin.paymenttype

import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface PaymentTypeListContract {

    interface View {
        fun load(list: ArrayList<PaymentTypeModel>)
        fun select(model: PaymentTypeModel)
        fun remove(model: PaymentTypeModel)
        fun openManager(model: PaymentTypeModel?)
        fun showLoading()
        fun stopLoading()
        fun showError(error: String)
    }

    interface Presenter {
        fun load()
        fun create()
        fun select(model: PaymentTypeModel)
        fun edit(model: PaymentTypeModel)
        fun delete(model: PaymentTypeModel)
    }

    interface OnSelectedListener {
        fun onSelect(model: PaymentTypeModel)
    }

    interface OnAdapterClickListener {
        fun onSelect(model: PaymentTypeModel)
        fun onEdit(model: PaymentTypeModel)
        fun onDelete(model: PaymentTypeModel)
    }
}