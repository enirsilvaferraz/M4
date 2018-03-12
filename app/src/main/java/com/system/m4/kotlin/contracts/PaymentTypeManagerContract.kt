package com.system.m4.kotlin.contracts

import com.system.m4.kotlin.model.entity.PaymentTypeModel

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface PaymentTypeManagerContract {

    interface View {
        fun fillFields(model: PaymentTypeModel)
        fun returnData(model: PaymentTypeModel?)
        fun showError(error: String)
        fun showLoading()
        fun stopLoading()
    }

    interface Presenter {
        fun init(model: PaymentTypeModel?)
        fun done(name: String)
        fun cancel()
    }

    interface OnCompleteListener {
        fun onComplete(model: PaymentTypeModel)
    }
}