package com.system.m4.kotlin.paymenttype.list

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.m4.R
import com.system.m4.kotlin.paymenttype.PaymentTypeModel
import com.system.m4.kotlin.paymenttype.manager.PaymentTypeManagerDialog
import kotlinx.android.synthetic.main.dialog_paymenttype_list.*
import java.util.*
import javax.inject.Inject

/**
 * Created by enirs on 30/08/2017.
 * Activity from PaymentType
 */
class PaymentTypeListDialog : DialogFragment(), PaymentTypeListContract.View, Toolbar.OnMenuItemClickListener {

    private lateinit var mListener: PaymentTypeListContract.OnSelectedListener

    @Inject
    lateinit var mPresenter: PaymentTypeListContract.Presenter

    init {
        PaymentTypeListComponent.injectObject(this)
    }

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

        mToolbar.setOnMenuItemClickListener(this)
        mToolbar.inflateMenu(R.menu.menu_crud_list)

        mRecyclerPaymentType.layoutManager = LinearLayoutManager(view!!.context)
        mRecyclerPaymentType.adapter = PaymentTypeAdapter(mPresenter)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onInit()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId!!.equals(R.id.menu_crud_list_add_new)) {
            mPresenter.onAddNewClicked()
            return true
        } else return false
    }

    /**
     * MVP
     */
    override fun loadList(list: ArrayList<PaymentTypeModel>) {
        (mRecyclerPaymentType.adapter as PaymentTypeAdapter).updateList(list)
    }

    override fun addOrUpdateItem(model: PaymentTypeModel) {
        (mRecyclerPaymentType.adapter as PaymentTypeAdapter).addOrUpdateItem(model)
    }

    override fun selectItem(model: PaymentTypeModel) {
        mListener.onSelect(model)
    }

    override fun remove(model: PaymentTypeModel) {
        (mRecyclerPaymentType.adapter as PaymentTypeAdapter).deleteItem(model)
    }

    override fun openManager(model: PaymentTypeModel?) {
        PaymentTypeManagerDialog.instance(model, mPresenter).show(fragmentManager, PaymentTypeManagerDialog.TAG)
    }

    override fun showLoading() {
        mProgressPayment.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        mProgressPayment.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}