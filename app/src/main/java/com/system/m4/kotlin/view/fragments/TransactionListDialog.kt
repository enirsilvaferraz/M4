package com.system.m4.kotlin.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.m4.R
import com.system.m4.kotlin.view.adapters.HomeAdapter
import com.system.m4.labs.vos.TransactionVO
import com.system.m4.labs.vos.VOItemListInterface
import kotlinx.android.synthetic.main.dialog_transaction_list.*

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

        mRecyclerPaymentType.layoutManager = LinearLayoutManager(view!!.context)

        mRecyclerPaymentType.adapter = HomeAdapter(object : HomeAdapter.Listener {
            override fun onClickVO(vo: VOItemListInterface, view: View) = showTransactionManager(vo)
            override fun onLongClickVO(vo: VOItemListInterface, view: View): Boolean = false
        })

        (mRecyclerPaymentType.adapter as HomeAdapter).addCurrentList(arguments.getParcelableArrayList<TransactionVO>("LIST"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                1 -> mRecyclerPaymentType.adapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * LISTENERS
     */
    private fun showTransactionManager(vo: VOItemListInterface) {
        if (vo is TransactionVO) {
            TransactionManagerDialog.newInstance(vo, this, 1).show(fragmentManager, TransactionManagerDialog::class.java.simpleName)
        }
    }
}