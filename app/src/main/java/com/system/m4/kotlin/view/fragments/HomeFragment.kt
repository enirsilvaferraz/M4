package com.system.m4.kotlin.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.contracts.HomeContract
import com.system.m4.kotlin.view.activities.MainActivity
import com.system.m4.kotlin.view.adapters.HomeAdapter
import com.system.m4.labs.vos.TagSummaryVO
import com.system.m4.labs.vos.TransactionVO
import com.system.m4.labs.vos.VOItemListInterface
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment : Fragment(), HomeContract.View {

    private var mRecyclerview: RecyclerView? = null

    private var presenter: HomeContract.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerview = view!!.findViewById(R.id.home_recyclerview)
        mRecyclerview!!.layoutManager = LinearLayoutManager(context)
        mRecyclerview!!.itemAnimator = DefaultItemAnimator()
        mRecyclerview!!.adapter = HomeAdapter(HomeListener())

        this.presenter!!.init(arguments.getInt(RELATIVE_POSITION))
    }

    fun requestListTransaction() {
        presenter!!.requestListTransaction()
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun setListTransactions(listVo: List<VOItemListInterface>) {
        val adapter = mRecyclerview!!.adapter as HomeAdapter
        adapter.clearList()
        adapter.addCurrentList(listVo)
    }

    override fun showTransactionDialog(vo: TransactionVO) {
        (activity as MainActivity).showTransactionDialog(vo)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMessage(template: Int, param: Int) {
        val message = getString(template, getString(param))
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun openDeleteDialog(item: TransactionVO) {
        JavaUtils.AndroidUtil.showAlertDialog(context, R.string.system_message_request_delete
        ) { dialogInterface, i -> presenter!!.onConfirmDeleteClicked(item) }
    }

    override fun showListTransaction(item: TagSummaryVO) {
        val dialogFragment = TransactionListDialog.instance(ArrayList(item.transactions))
        dialogFragment.show(fragmentManager, TransactionListDialog::class.java.simpleName)
    }

    override fun showPoupu(viewClicked: View, vo: TransactionVO) {

        val popupMenu = PopupMenu(context, viewClicked)
        popupMenu.inflate(R.menu.menu_transaction)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_copy -> {
                    presenter!!.requestCopy(vo)
                    return@OnMenuItemClickListener true
                }

                R.id.action_delete -> {
                    presenter!!.requestDelete(vo)
                    return@OnMenuItemClickListener true
                }
            }

            false
        })

        val menuHelper = MenuPopupHelper(context, popupMenu.menu as MenuBuilder, viewClicked)
        menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }

    private inner class HomeListener : HomeAdapter.Listener {

        override fun onClickVO(vo: VOItemListInterface, view: View) {
            presenter!!.onClickVO(vo)
        }

        override fun onLongClickVO(vo: VOItemListInterface, view: View): Boolean {
            return presenter!!.onLongClickVO(vo, view)
        }
    }

    companion object {

        val RELATIVE_POSITION = "RELATIVE_POSITION"
    }
}
