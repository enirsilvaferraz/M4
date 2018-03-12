package com.system.m4.kotlin.view.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.m4.R
import com.system.m4.kotlin.infrastructure.BaseDialogFragment
import com.system.m4.kotlin.view.adapters.HomeAdapter
import com.system.m4.labs.vos.TransactionVO
import com.system.m4.labs.vos.VOInterface
import com.system.m4.labs.vos.VOItemListInterface

/**
 * Created by enirs on 11/10/2017.
 * Dialog for transaction
 */
class TransactionListDialog : DialogFragment() {

    /**
     * STATIC
     */
    companion object {
        fun instance(list: ArrayList<TransactionVO>): TransactionListDialog {

            val bundle = Bundle()
            bundle.putParcelableArrayList("LIST", list)

            val dialog = TransactionListDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    /**
     * LIFECYCLE
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_transaction_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view!!.findViewById(R.id.mRecyclerPaymentType) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        recyclerView.adapter = HomeAdapter(object : HomeAdapter.Listener {
            override fun onClickVO(vo: VOItemListInterface, view: View) = showTransactionManager(vo, recyclerView)
            override fun onLongClickVO(vo: VOItemListInterface, view: View): Boolean = false
        })

        (recyclerView.adapter as HomeAdapter).addCurrentList(arguments.getParcelableArrayList<TransactionVO>("LIST"))
    }

    /**
     * LISTENERS
     */
    private fun showTransactionManager(vo: VOItemListInterface, recyclerView: RecyclerView) {

        if (vo is TransactionVO) {

            val dialogFragment = TransactionManagerDialog.newInstance(vo)
            dialogFragment.dialogListener = object : BaseDialogFragment.DialogListener {
                override fun onFinish(vo: VOInterface<*>) {
                    recyclerView.adapter.notifyDataSetChanged()
                }
            }
            dialogFragment.show(fragmentManager, TransactionManagerDialog::class.java.simpleName)
        }
    }
}