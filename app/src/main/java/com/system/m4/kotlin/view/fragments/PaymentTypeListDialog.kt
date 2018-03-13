package com.system.m4.kotlin.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.m4.R
import com.system.m4.infrastructure.Constants
import com.system.m4.kotlin.contracts.PaymentTypeListContract
import com.system.m4.kotlin.infrastructure.di.DaggerFactory
import com.system.m4.kotlin.model.entity.PaymentTypeModel
import com.system.m4.kotlin.view.adapters.PaymentTypeAdapter
import kotlinx.android.synthetic.main.dialog_paymenttype_list.*
import java.util.*
import javax.inject.Inject

/**
 * Created by enirs on 30/08/2017.
 * Activity from PaymentType
 */
class PaymentTypeListDialog : DialogFragment(), PaymentTypeListContract.View, Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var mPresenter: PaymentTypeListContract.Presenter

    init {
        DaggerFactory.injectObject(this)
    }

    /**
     * STATIC
     */
    companion object {
        fun instance(target: Fragment, requestCode: Int): PaymentTypeListDialog {
            val dialog = PaymentTypeListDialog()
            dialog.setTargetFragment(target, requestCode)
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
        val intent = Intent()
        intent.putExtra(Constants.VALUE_BUNDLE, model)
        targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
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