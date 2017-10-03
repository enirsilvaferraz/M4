package com.system.m4.kotlin.transaction

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
import com.system.m4.kotlin.infrastructure.BaseDialogFragment
import com.system.m4.kotlin.paymenttype.PaymentTypeListContract
import com.system.m4.kotlin.paymenttype.PaymentTypeListDialog
import com.system.m4.kotlin.paymenttype.PaymentTypeModel
import com.system.m4.kotlin.tags.TagListContract
import com.system.m4.kotlin.tags.TagListDialog
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.views.components.dialogs.NumberComponentDialog
import com.system.m4.views.components.dialogs.TextComponentDialog
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.Transaction
import com.system.m4.views.vos.VOInterface
import java.util.*

/**
 * Created by Enir on 12/04/2017.
 * Dialog de manutencao de transacoes
 */

class TransactionManagerDialog : BaseDialogFragment(), TransactionManagerContract.View {

    private lateinit var tvPaymentDate: TextView
    private lateinit var tvPurchaseDate: TextView
    private lateinit var tvValue: TextView
    private lateinit var tvPaymentType: TextView
    private lateinit var tvContent: TextView

    private lateinit var llPaymentDate: LinearLayout
    private lateinit var llPurchaseDate: LinearLayout
    private lateinit var llValue: LinearLayout
    private lateinit var llPaymentType: LinearLayout
    private lateinit var llContent: LinearLayout

    private lateinit var presenter: TransactionManagerContract.Presenter

    /*
     * LIFECYLCE
     */

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.dialog_transaction_manager, container, false)

        tvPaymentDate = rootView.findViewById(R.id.transaction_manager_textview_payment_date) as TextView
        tvPurchaseDate = rootView.findViewById(R.id.transaction_manager_textview_purchase_date) as TextView
        tvValue = rootView.findViewById(R.id.transaction_manager_textview_value) as TextView
        tvPaymentType = rootView.findViewById(R.id.transaction_manager_textview_payment_type) as TextView
        tvContent = rootView.findViewById(R.id.transaction_manager_textview_content) as TextView

        llPaymentDate = rootView.findViewById(R.id.transaction_manager_action_payment_date) as LinearLayout
        llPurchaseDate = rootView.findViewById(R.id.transaction_manager_action_purchase_date) as LinearLayout
        llValue = rootView.findViewById(R.id.transaction_manager_action_value) as LinearLayout
        llPaymentType = rootView.findViewById(R.id.transaction_manager_action_payment_type) as LinearLayout
        llContent = rootView.findViewById(R.id.transaction_manager_action_content) as LinearLayout

        llPaymentDate.setOnClickListener { actionPaymentDate() }
        llPurchaseDate.setOnClickListener { actionPurchaseDate() }
        llValue.setOnClickListener { actionValue() }
        llPaymentType.setOnClickListener { actionPaymentType() }
        llContent.setOnClickListener { actionContent() }

        llPaymentDate.setOnLongClickListener { clearPaymentDate() }
        llPurchaseDate.setOnLongClickListener { clearPurchaseDate() }
        llValue.setOnLongClickListener { clearValue() }
        llPaymentType.setOnLongClickListener { clearPaymentType() }
        llContent.setOnLongClickListener { clearContent() }

        presenter = TransactionManagerPresenter(this)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transaction = arguments.getParcelable<Transaction>(Constants.BUNDLE_TRANSACTION_VO)
        presenter.init(transaction)
    }

    override fun configureModel(transaction: Transaction) {
        setTitle(transaction.tag.name)
        presenter.setTags(transaction.tag)
        presenter.setContent(transaction.content)
        presenter.setPaymentDate(transaction.paymentDate)
        presenter.setPurchaseDate(transaction.purchaseDate)
        presenter.setPaymentType(transaction.paymentType)
        presenter.setValue(transaction.price)
    }

    /*
    * ACTIONS
    */

    private fun actionPaymentDate() {
        presenter.requestPaymentDateDialog(tvPaymentDate.text.toString())
    }

    private fun actionPurchaseDate() {
        presenter.requestPurchaseDateDialog(tvPurchaseDate.text.toString())
    }

    private fun actionValue() {
        presenter.requestValueDialog(tvValue.text.toString())
    }

    private fun actionPaymentType() {
        PaymentTypeListDialog.instance(object : PaymentTypeListContract.OnSelectedListener {
            override fun onSelect(model: PaymentTypeModel) {
                val vo = PaymentTypeVO()
                vo.key = model.key
                vo.name = model.name
                vo.color = model.color
                presenter.setPaymentType(vo)

            }
        }).show(fragmentManager, PaymentTypeListDialog::class.java.simpleName)
    }

    private fun actionContent() {
        presenter.requestContentDialog(tvContent.text.toString())
    }

    private fun clearPaymentDate(): Boolean {
        presenter.clearPaymentDate()
        tvPaymentDate.setText(R.string.system_empty_field)
        return true
    }

    private fun clearPurchaseDate(): Boolean {
        presenter.clearPurchaseDate()
        tvPurchaseDate.setText(R.string.system_empty_field)
        return true
    }

    private fun clearValue(): Boolean {
        presenter.clearPrice()
        tvValue.setText(R.string.system_empty_field)
        return true
    }

    private fun clearPaymentType(): Boolean {
        presenter.clearPaymentType()
        tvPaymentType.setText(R.string.system_empty_field)
        return true
    }

    private fun clearContent(): Boolean {
        presenter.clearContent()
        tvContent.setText(R.string.system_empty_field)
        return true
    }

    override fun setPaymentDate(value: String) {
        tvPaymentDate.text = value
    }

    override fun setPurchaseDate(value: String) {
        tvPurchaseDate.text = value
    }

    override fun setValue(value: String) {
        tvValue.text = value
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

    override fun showValueDialog(value: Double?) {
        NumberComponentDialog.newInstance(R.string.transaction_price, value) { valueParam -> presenter.setValue(JavaUtils.NumberUtil.removeFormat(valueParam)) }.show(childFragmentManager)
    }

    override fun showContentDialog(value: String?) {
        TextComponentDialog.newInstance(R.string.transaction_content, value) { valueParam -> presenter.setContent(valueParam) }.show(childFragmentManager)
    }

    override fun showPaymentDateDialog(date: Date) {
        JavaUtils.AndroidUtil.showDatePicker(context, date) { view, year, month, dayOfMonth -> presenter.setPaymentDate(JavaUtils.DateUtil.getDate(year, month, dayOfMonth)) }
    }

    override fun showPurchaseDateDialog(date: Date) {
        JavaUtils.AndroidUtil.showDatePicker(context, date) { view, year, month, dayOfMonth -> presenter.setPurchaseDate(JavaUtils.DateUtil.getDate(year, month, dayOfMonth)) }
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
                val vo = TagVO()
                vo.key = model.key
                vo.name = model.name
                presenter.setTags(vo)
            }
        }).show(fragmentManager, TagListDialog::class.java.simpleName)
    }

    companion object {

        fun newInstance(transaction: Transaction): TransactionManagerDialog {
            val bundle = Bundle()
            bundle.putParcelable(Constants.BUNDLE_TRANSACTION_VO, transaction)

            val fragment = TransactionManagerDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}
