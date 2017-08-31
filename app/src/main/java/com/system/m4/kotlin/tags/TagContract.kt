package com.system.m4.kotlin.tags

import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Contract
 */
interface TagContract {

    interface View {
        fun showError(error: String)
        fun showLoading()
        fun stopLoading()
        fun loadData(list: ArrayList<DataTag>)
        fun addData(model: DataTag)
        fun updateData(model: DataTag)
        fun removeData(model: DataTag)
        fun openDialogManager(model: DataTag)
    }

    interface Presenter{
        fun init()
        fun selectItem(model: DataTag)
    }
}