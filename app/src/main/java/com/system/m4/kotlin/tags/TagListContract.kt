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
        fun updateData(model: TagModel)
        fun removeData(model: TagModel)
        fun openDialogManager(model: TagModel)
    }

    interface Presenter{
        fun init()
        fun selectItem(model: TagModel)
    }
}