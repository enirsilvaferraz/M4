package com.system.m4.kotlin.tags

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface TagManagerContract {

    interface View {
        fun fillFields(model: TagModel)
        fun configureFields(list: ArrayList<String>)
        fun returnData(model: TagModel?)
        fun showError(error: String)
        fun showLoading()
        fun stopLoading()
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