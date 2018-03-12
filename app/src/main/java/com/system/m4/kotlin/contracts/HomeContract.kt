package com.system.m4.kotlin.contracts

import android.content.Context

import com.system.m4.labs.vos.TagSummaryVO
import com.system.m4.labs.vos.TransactionVO
import com.system.m4.labs.vos.VOItemListInterface

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

interface HomeContract {

    /**
     *
     */
    interface View {

        fun getContext(): Context

        fun setPresenter(presenter: Presenter)

        fun setListTransactions(listVo: List<VOItemListInterface>)

        fun showTransactionDialog(vo: TransactionVO)

        fun showError(message: String)

        fun showSuccessMessage(template: Int, param: Int)

        fun openDeleteDialog(item: TransactionVO)

        fun showListTransaction(item: TagSummaryVO)

        fun showPoupu(viewClicked: android.view.View, vo: TransactionVO)
    }

    /**
     *
     */
    interface Presenter {

        fun init(relativePosition: Int)

        fun requestListTransaction()

        fun selectItem(vo: TransactionVO)

        fun requestCopy(item: TransactionVO)

        fun requestDelete(item: TransactionVO)

        fun onConfirmDeleteClicked(item: TransactionVO)

        fun requestShowListTransaction(item: TagSummaryVO)

        fun onClickVO(vo: VOItemListInterface)

        fun onLongClickVO(vo: VOItemListInterface, view: android.view.View): Boolean
    }
}
