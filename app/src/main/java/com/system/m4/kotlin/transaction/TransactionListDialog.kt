package com.system.m4.kotlin.transaction

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.m4.R
import com.system.m4.kotlin.infrastructure.BaseDialogFragment
import com.system.m4.views.vos.Transaction
import com.system.m4.views.vos.VOInterface

/**
 * Created by enirs on 11/10/2017.
 * Dialog for transaction
 */
class TransactionListDialog : DialogFragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar

    /**
     * STATIC
     */
    companion object {
        fun instance(list: ArrayList<Transaction>): TransactionListDialog {

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

        mRecyclerView = view!!.findViewById<RecyclerView>(R.id.dialog_list_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = TransactionAdapter(arguments.getParcelableArrayList<Transaction>("LIST"), object : TransactionAdapter.OnClickListener {

            override fun onClick(vo: Transaction) {
                val dialogFragment = TransactionManagerDialog.newInstance(vo)
                dialogFragment.dialogListener = object : BaseDialogFragment.DialogListener {
                    override fun onFinish(vo: VOInterface<*>) {
                        mRecyclerView.adapter.notifyDataSetChanged()
                    }
                }
                dialogFragment.show(fragmentManager, TransactionManagerDialog::class.java.simpleName)
            }
        })
    }
}