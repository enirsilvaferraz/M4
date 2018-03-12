package com.system.m4.kotlin.presenters

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.contracts.MainContract
import com.system.m4.kotlin.view.adapters.MainPageAdapter
import com.system.m4.labs.vos.TagVO
import java.util.*

/**
 * Created by eferraz on 29/07/17.
 */

class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {

    init {
        configureTitle(MainPageAdapter.PAGE_MIDDLE)
    }

    override fun requestTransactionManager() {
        mView.requestTransactionManagerDialog() // Colocar PLaceholder e remover if list == null do adapter
    }

    override fun requestTransactionDialog(vo: TagVO) {
        mView.showTransactionDialog(vo)
    }

    override fun configureTitle(position: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, position - MainPageAdapter.PAGE_MIDDLE)
        mView.setMainTitle(JavaUtils.DateUtil.format(calendar.time, JavaUtils.DateUtil.MMMM_DE_YYYY))
    }

}
