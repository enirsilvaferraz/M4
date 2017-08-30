package com.system.m4.kotlin.tags

import java.util.*

/**
 * Created by enirs on 30/08/2017.
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
    }

    interface Presenter{

    }
}