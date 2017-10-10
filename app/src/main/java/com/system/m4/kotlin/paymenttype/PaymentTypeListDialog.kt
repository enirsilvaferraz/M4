package com.system.m4.kotlin.paymenttype

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.system.m4.R
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Activity from PaymentType
 */
class PaymentTypeListDialog : DialogFragment(), PaymentTypeListContract.View, Toolbar.OnMenuItemClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgress: ProgressBar
    private lateinit var mToolbar: Toolbar

    private lateinit var mListener: PaymentTypeListContract.OnSelectedListener
    private lateinit var mPresenter: PaymentTypeListContract.Presenter

    /**
     * STATIC
     */
    companion object {
        fun instance(listener: PaymentTypeListContract.OnSelectedListener): PaymentTypeListDialog {
            val dialog = PaymentTypeListDialog()
            dialog.mListener = listener
            return dialog
        }
    }

    /**
     * LIFECYCLE
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_paymenttype_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar = view!!.findViewById<Toolbar>(R.id.dialog_toolbar)
        mToolbar.setOnMenuItemClickListener(this)
        mToolbar.inflateMenu(R.menu.menu_crud_list)

        mRecyclerView = view.findViewById<RecyclerView>(R.id.dialog_list_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = PaymentTypeAdapter(object : PaymentTypeListContract.OnAdapterClickListener {

            override fun onSelect(model: PaymentTypeModel) {
                mPresenter.select(model)
            }

            override fun onEdit(model: PaymentTypeModel) {
                mPresenter.edit(model)
            }

            override fun onDelete(model: PaymentTypeModel) {
                mPresenter.delete(model)
            }
        })

        mProgress = view.findViewById<ProgressBar>(R.id.dialog_progress)

        mPresenter = PaymentTypeListPresenter(this)
        mPresenter.load()
    }

    /**
     * LISTENERS
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId!!.equals(R.id.menu_crud_list_add_new)) {
            mPresenter.create()
        }
        return true
    }

    /**
     * MVP
     */
    override fun load(list: ArrayList<PaymentTypeModel>) {
        (mRecyclerView.adapter as PaymentTypeAdapter).updateList(list)
    }

    override fun select(model: PaymentTypeModel) {
        mListener.onSelect(model)
        dismiss()
    }

    override fun remove(model: PaymentTypeModel) {
        (mRecyclerView.adapter as PaymentTypeAdapter).deleteItem(model)
    }

    override fun openManager(model: PaymentTypeModel?) {
        PaymentTypeManagerDialog.instance(model, object : PaymentTypeManagerContract.OnCompleteListener {
            override fun onComplete(model: PaymentTypeModel) {
                (mRecyclerView.adapter as PaymentTypeAdapter).addOrUpdateItem(model)
            }
        }).show(fragmentManager, PaymentTypeManagerDialog.TAG)
    }

    override fun showLoading() {
        mProgress.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        mProgress.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}