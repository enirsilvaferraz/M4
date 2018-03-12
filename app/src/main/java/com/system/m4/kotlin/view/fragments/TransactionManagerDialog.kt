package com.system.m4.kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.system.m4.R
import com.system.m4.infrastructure.Constants
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.contracts.PaymentTypeListContract
import com.system.m4.kotlin.contracts.TagListContract
import com.system.m4.kotlin.contracts.TransactionManagerContract
import com.system.m4.kotlin.infrastructure.BaseDialogFragment
import com.system.m4.kotlin.model.entity.PaymentTypeModel
import com.system.m4.kotlin.model.entity.TagModel
import com.system.m4.kotlin.presenters.TransactionManagerPresenter
import com.system.m4.kotlin.view.components.NumberComponentDialog
import com.system.m4.kotlin.view.components.TextComponentDialog
import com.system.m4.labs.vos.PaymentTypeVO
import com.system.m4.labs.vos.TagVO
import com.system.m4.labs.vos.TransactionVO
import com.system.m4.labs.vos.VOInterface
import java.util.*

/**
 * Created by Enir on 12/04/2017.
 * Dialog de manutencao de transacoes
 */

class TransactionManagerDialog : BaseDialogFragment(), TransactionManagerContract.View {

    private lateinit var tvPaymentDate: TextView
    private lateinit var tvPurchaseDate: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvRefund: TextView
    private lateinit var tvPaymentType: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvParcels: TextView
    private lateinit var tvAlreadyPaid: TextView

    private lateinit var llPaymentDate: LinearLayout
    private lateinit var llPurchaseDate: LinearLayout
    private lateinit var llPrice: LinearLayout
    private lateinit var llRefund: LinearLayout
    private lateinit var llPaymentType: LinearLayout
    private lateinit var llContent: LinearLayout
    private lateinit var llParcels: LinearLayout
    private lateinit var llAlreadyPaid: LinearLayout

    private lateinit var presenter: TransactionManagerContract.Presenter

    /*
     * LIFECYLCE
     */

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.dialog_transaction_manager, container, false)

        tvPaymentDate = rootView.findViewById(R.id.transaction_manager_textview_payment_date)
        tvPurchaseDate = rootView.findViewById(R.id.transaction_manager_textview_purchase_date)
        tvPrice = rootView.findViewById(R.id.transaction_manager_textview_price)
        tvRefund = rootView.findViewById(R.id.transaction_manager_textview_refund)
        tvPaymentType = rootView.findViewById(R.id.transaction_manager_textview_payment_type)
        tvContent = rootView.findViewById(R.id.transaction_manager_textview_content)
        tvParcels = rootView.findViewById(R.id.transaction_manager_textview_parcels)
        tvAlreadyPaid = rootView.findViewById(R.id.transaction_manager_textview_already_paid)

        llPaymentDate = rootView.findViewById(R.id.transaction_manager_action_payment_date)
        llPurchaseDate = rootView.findViewById(R.id.transaction_manager_action_purchase_date)
        llPrice = rootView.findViewById(R.id.transaction_manager_action_price)
        llRefund = rootView.findViewById(R.id.transaction_manager_action_refund)
        llPaymentType = rootView.findViewById(R.id.transaction_manager_action_payment_type)
        llContent = rootView.findViewById(R.id.transaction_manager_action_content)
        llParcels = rootView.findViewById(R.id.transaction_manager_action_parcels)
        llAlreadyPaid = rootView.findViewById(R.id.transaction_manager_action_already_paid)

        llPaymentDate.setOnClickListener { presenter.onPaymentDateClick() }
        llPurchaseDate.setOnClickListener { presenter.onPurchaseDateClick() }
        llPrice.setOnClickListener { presenter.onPriceClick() }
        llRefund.setOnClickListener { presenter.onRefundClick() }
        llPaymentType.setOnClickListener { presenter.onPaymentTypeClick() }
        llContent.setOnClickListener { presenter.onContentClick() }
        llParcels.setOnClickListener { presenter.onParcelsClick() }
        llAlreadyPaid.setOnClickListener { presenter.onAlreadyPaidClick() }

        llPaymentDate.setOnLongClickListener { presenter.onPaymentDateLongClick() }
        llPurchaseDate.setOnLongClickListener { presenter.onPurchaseDateLongClick() }
        llPrice.setOnLongClickListener { presenter.onPriceLongClick() }
        llRefund.setOnLongClickListener { presenter.onRefundLongClick() }
        llPaymentType.setOnLongClickListener { presenter.onPaymentTypeLongClick() }
        llContent.setOnLongClickListener { presenter.onContentLongClick() }
        llParcels.setOnLongClickListener { presenter.onParcelsLongClick() }

        presenter = TransactionManagerPresenter(this)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transaction = arguments.getParcelable<TransactionVO>(Constants.BUNDLE_TRANSACTION_VO)
        presenter.init(transaction)
    }

    /*
     * MVP
     */

    override fun setToolbarTitle(titleString: String) {
        setTitle(titleString)
    }

    override fun setPaymentDate(value: String) {
        tvPaymentDate.text = value
    }

    override fun setPurchaseDate(value: String) {
        tvPurchaseDate.text = value
    }

    override fun setPrice(value: String) {
        tvPrice.text = value
    }

    override fun setRefund(value: String) {
        tvRefund.text = value
    }

    override fun setTags(value: String) {
        mToolbar.setTitle(value)
    }

    override fun setPaymentType(value: String) {
        tvPaymentType.text = value
    }

    override fun setContent(value: String) {
        tvContent.text = value
    }

    override fun setParcels(value: String) {
        tvParcels.text = value
    }

    override fun setAlreadyPaid(value: String) {
        tvAlreadyPaid.text = value
    }

    override fun showPaymentTypeDialog() {
        PaymentTypeListDialog.instance(object : PaymentTypeListContract.OnSelectedListener {
            override fun onSelect(model: PaymentTypeModel) {
                presenter.setPaymentType(PaymentTypeVO(model.key, model.name, model.color))
            }
        }).show(fragmentManager, PaymentTypeListDialog::class.java.simpleName)
    }

    override fun showPriceDialog(value: Double?) {
        NumberComponentDialog.newInstance(
                R.string.transaction_price, value,
                object : NumberComponentDialog.OnFinishListener {
                    override fun onFinish(value: String) {
                        presenter.setPrice(JavaUtils.NumberUtil.removeFormat(value))
                    }
                }
        ).show(childFragmentManager)
    }

    override fun showRefundDialog(value: Double?) {
        NumberComponentDialog.newInstance(R.string.transaction_refund, value, object : NumberComponentDialog.OnFinishListener {
            override fun onFinish(value: String) {
                presenter.setRefund(JavaUtils.NumberUtil.removeFormat(value))
            }
        }).show(childFragmentManager)
    }

    override fun showContentDialog(value: String?) {
        TextComponentDialog.newInstance(R.string.transaction_content, value, object : TextComponentDialog.OnFinishListener {
            override fun onFinish(value: String) {
                presenter.setContent(value)
            }
        }).show(childFragmentManager)
    }

    override fun showParcelsDialog(value: String?) {
        TextComponentDialog.newInstance(R.string.transaction_parcels, value, object : TextComponentDialog.OnFinishListener {
            override fun onFinish(value: String) {
                presenter.setParcels(value)
            }
        }).show(childFragmentManager)
    }

    override fun showPaymentDateDialog(date: Date?) {
        JavaUtils.AndroidUtil.showDatePicker(context, date) { _, year, month, dayOfMonth ->
            presenter.setPaymentDate(JavaUtils.DateUtil.getDate(year, month + 1, dayOfMonth))
        }
    }

    override fun showPurchaseDateDialog(date: Date?) {
        JavaUtils.AndroidUtil.showDatePicker(context, date) { _, year, month, dayOfMonth ->
            presenter.setPurchaseDate(JavaUtils.DateUtil.getDate(year, month + 1, dayOfMonth))
        }
    }

    override fun dismissDialog(vo: VOInterface<*>) {
        dismiss()
        dialogListener?.onFinish(vo)
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun showError(template: Int, param: Int) {
        Toast.makeText(context, getString(template, getString(param)), Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMessage(template: Int, param: Int) {
        val message = getString(template, getString(param))
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /*
     * LISTENERS
     */

    override fun onDoneClick() {
        presenter.save()
    }

    override fun onTitleClick() {
        TagListDialog.instance(object : TagListContract.OnSelectedListener {
            override fun onSelect(model: TagModel) {
                presenter.setTags(TagVO(model.key, null, model.name))
            }
        }).show(fragmentManager, TagListDialog::class.java.simpleName)
    }

    companion object {

        fun newInstance(transaction: TransactionVO): TransactionManagerDialog {
            val bundle = Bundle()
            bundle.putParcelable(Constants.BUNDLE_TRANSACTION_VO, transaction)

            val fragment = TransactionManagerDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}
