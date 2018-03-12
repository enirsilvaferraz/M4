package com.system.m4.kotlin.contracts

import com.system.m4.kotlin.model.entity.PaymentTypeModel
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

    interface Presenter : OnAdapterClickListener, PaymentTypeManagerContract.OnCompleteListener {
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