package com.system.m4.kotlin.tags

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface TagManagerContract {

    interface View {
        fun showError(error: String)
        fun showLoading()
        fun stopLoading()
        fun returnData(model: TagModel?)
        fun fillFields(model: TagModel)
    }

    interface Presenter {
        fun init(model: TagModel?)
        fun done(name: String)
        fun cancel()
    }

    interface OnCompleteListener {
        fun onComplete(model: TagModel)
    }
}