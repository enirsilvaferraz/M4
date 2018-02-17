package com.system.m4.kotlin.tags

import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface TagListContract {

    interface View {
        fun loadTags(list: ArrayList<TagModel>)
        fun select(model: TagModel)
        fun remove(model: TagModel)
        fun openManager(model: TagModel?)
        fun showLoading()
        fun stopLoading()
        fun showError(error: String)
    }

    interface Presenter : TagListContract.OnAdapterClickListener {
        fun init()
        fun create()
        fun addList()
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