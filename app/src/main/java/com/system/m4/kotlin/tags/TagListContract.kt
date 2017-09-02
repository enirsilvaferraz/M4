package com.system.m4.kotlin.tags

import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface TagListContract {

    interface View {
        fun showError(error: String)
        fun showLoading()
        fun stopLoading()
        fun loadData(list: ArrayList<TagModel>)
        fun addData(model: TagModel)
        fun removeData(model: TagModel)
        fun openDialogManager(model: TagModel?)
        fun select(model: TagModel)
    }

    interface Presenter {
        fun init()
        fun selectItem(model: TagModel)
        fun createModel(model: TagModel)
        fun addItem()
        fun edit(model: TagModel)
        fun delete(model: TagModel)
    }

    interface OnSelectedListener {
        fun onSelect(model: TagModel)
    }

    interface OnAdapterClickListener {
        fun onSelect(model: TagModel)
        fun onEdit(model: TagModel)
        fun onDelete(model: TagModel)
    }
}