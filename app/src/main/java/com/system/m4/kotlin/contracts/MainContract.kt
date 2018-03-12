package com.system.m4.kotlin.contracts

import com.system.m4.labs.vos.TagVO
import com.system.m4.labs.vos.TransactionVO

/**
 * Created by eferraz on 29/07/17.
 * For M4
 */

interface MainContract {

    interface View {

        fun requestTransactionManagerDialog()

        fun showTransactionDialog(vo: TagVO)

        fun showTransactionDialog(vo: TransactionVO)

        fun setMainTitle(title: String)
    }

    interface Presenter {

        fun requestTransactionManager()

        fun requestTransactionDialog(vo: TagVO)

        fun configureTitle(position: Int)
    }
}
