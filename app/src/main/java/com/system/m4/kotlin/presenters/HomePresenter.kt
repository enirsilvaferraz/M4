package com.system.m4.kotlin.presenters

import android.view.View
import com.system.m4.kotlin.contracts.HomeContract
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.infrastructure.listeners.SingleResultListener
import com.system.m4.kotlin.model.business.HomeBusiness
import com.system.m4.kotlin.model.business.TransactionBusiness
import com.system.m4.kotlin.model.entity.TransactionModel
import com.system.m4.labs.vos.*
import java.util.*

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomePresenter internal constructor(private val mView: HomeContract.View) : HomeContract.Presenter {

    private var date: Calendar? = null

    override fun init(relativePosition: Int) {
        date = Calendar.getInstance()
        date!!.add(Calendar.MONTH, relativePosition)
        requestListTransaction()
    }

    override fun requestListTransaction() {

        HomeBusiness().findHomeList(date!!.get(Calendar.YEAR), date!!.get(Calendar.MONTH), object : SingleResultListener<HomeVO> {

            override fun onSuccess(item: HomeVO) {
                configureHeaderList(item)
            }

            override fun onError(e: String) {
                mView.setListTransactions(ArrayList())
                mView.showError(e)
            }
        })
    }

    private fun configureHeaderList(item: HomeVO) {

        val listVO = ArrayList<VOItemListInterface>()

        if (!item.summaries.isEmpty()) {
            listVO.add(SubTitleVO("Resumo"))
            listVO.addAll(item.summaries)
            listVO.add(SpaceVO())
        }

        if (!item.pendingTransaction.isEmpty()) {
            listVO.add(SubTitleVO("Transações Pendentes"))
            listVO.addAll(item.pendingTransaction)
            listVO.add(SpaceVO())
        }

        if (!item.transactions1Q.transactions.isEmpty()) {
            listVO.add(SubTitleVO("Transações da 1a quinzena"))
            listVO.addAll(item.transactions1Q.transactions)
            listVO.add(AmountVO("Total", item.transactions1Q.amount))
            listVO.add(AmountVO("Pagamentos futuros", item.transactions1Q.valueNotPaid))
            listVO.add(SpaceVO())
        }

        if (!item.transactions2Q.transactions.isEmpty()) {
            listVO.add(SubTitleVO("Transações da 2a quinzena"))
            listVO.addAll(item.transactions2Q.transactions)
            listVO.add(AmountVO("Total", item.transactions2Q.amount))
            listVO.add(AmountVO("Pagamentos futuros", item.transactions2Q.valueNotPaid))
            listVO.add(SpaceVO())
        }

        for (key in item.groups.keys) {
            listVO.add(SubTitleVO(key.name))
            listVO.addAll(item.groups[key]!!.transactions)
            listVO.add(AmountVO("Total", item.groups[key]!!.amount))
            listVO.add(SpaceVO())
        }

        if (!item.tagSummary.isEmpty()) {
            listVO.add(SubTitleVO("Resumo das Tags"))
            listVO.addAll(item.tagSummary)
            listVO.add(SpaceVO())
        }

        mView.setListTransactions(listVO)
    }

    override fun selectItem(vo: TransactionVO) {
        mView.showTransactionDialog(vo)
    }

    override fun requestDelete(item: TransactionVO) {
        mView.openDeleteDialog(item)
    }

    override fun requestCopy(item: TransactionVO) {
        item.key = null
        mView.showTransactionDialog(item)
    }

    override fun onConfirmDeleteClicked(item: TransactionVO) {
        TransactionBusiness().delete(item, object : PersistenceListener<TransactionModel> {
            override fun onSuccess(transactionVO: TransactionModel) {
                requestListTransaction()
            }

            override fun onError(e: String) {
                mView.showError(e)
            }
        })
    }

    override fun requestShowListTransaction(item: TagSummaryVO) {
        mView.showListTransaction(item)
    }

    override fun onClickVO(vo: VOItemListInterface) {

        if (vo is TransactionVO) {
            selectItem(vo)
        } else if (vo is TagSummaryVO) {
            requestShowListTransaction(vo)
        }
    }

    override fun onLongClickVO(vo: VOItemListInterface, view: View): Boolean {

        if (vo is TransactionVO) {
            mView.showPoupu(view, vo)
            return true
        }

        return false
    }
}
