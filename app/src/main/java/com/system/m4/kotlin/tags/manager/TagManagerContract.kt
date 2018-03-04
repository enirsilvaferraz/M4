package com.system.m4.kotlin.tags.manager

import com.system.m4.kotlin.tags.TagModel

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface TagManagerContract {

    interface View {
        fun fillFields(model: TagModel)
        fun fillFieldParent(list: ArrayList<TagModel>, model: TagModel)
        fun returnData(model: TagModel?)
        fun showError(error: String)
        fun showLoading()
        fun stopLoading()
        fun enableParentSelection(enable: Boolean)
    }

    interface Presenter {
        fun init(model: TagModel?)
        fun done(name: String)
        fun cancel()
        fun configureParent(selected: TagModel)
    }

    interface OnCompleteListener {
        fun onComplete(model: TagModel)
    }
}