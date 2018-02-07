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
import com.system.m4.views.vos.TransactionVO
import com.system.m4.views.vos.VOInterface
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

    private lateinit var llPaymentDate: LinearLayout
    private lateinit var llPurchaseDate: LinearLayout
    private lateinit var llPrice: LinearLayout
    private lateinit var llRefund: LinearLayout
    private lateinit var llPaymentType: LinearLayout
    private lateinit var llContent: LinearLayout
    private lateinit var llParcels: LinearLayout

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

        llPaymentDate = rootView.findViewById(R.id.transaction_manager_action_payment_date)
        llPurchaseDate = rootView.findViewById(R.id.transaction_manager_action_purchase_date)
        llPrice = rootView.findViewById(R.id.transaction_manager_action_price)
        llRefund = rootView.findViewById(R.id.transaction_manager_action_refund)
        llPaymentType = rootView.findViewById(R.id.transaction_manager_action_payment_type)
        llContent = rootView.findViewById(R.id.transaction_manager_action_content)
        llParcels = rootView.findViewById(R.id.transaction_manager_action_parcels)

        llPaymentDate.setOnClickListener { actionPaymentDate() }
        llPurchaseDate.setOnClickListener { actionPurchaseDate() }
        llPrice.setOnClickListener { actionPrice() }
        llRefund.setOnClickListener { actionRefund() }
        llPaymentType.setOnClickListener { actionPaymentType() }
        llContent.setOnClickListener { actionContent() }
        llParcels.setOnClickListener { actionParcels() }

        llPaymentDate.setOnLongClickListener { clearPaymentDate() }
        llPurchaseDate.setOnLongClickListener { clearPurchaseDate() }
        llPrice.setOnLongClickListener { clearPrice() }
        llRefund.setOnLongClickListener { clearRefund() }
        llPaymentType.setOnLongClickListener { clearPaymentType() }
        llContent.setOnLongClickListener { clearContent() }
        llParcels.setOnLongClickListener { clearParcels() }

        presenter = TransactionManagerPresenter(this)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transaction = arguments.getParcelable<TransactionVO>(Constants.BUNDLE_TRANSACTION_VO)
        presenter.init(transaction)
    }

    override fun configureModel(transaction: TransactionVO) {
        setTitle(transaction.tag.name)
        presenter.setTags(transaction.tag)
        presenter.setContent(transaction.content)
        presenter.setPaymentDate(transaction.paymentDate)
        presenter.setPurchaseDate(transaction.purchaseDate)
        presenter.setPaymentType(transaction.paymentType)
        presenter.setPrice(transaction.price)
        presenter.setRefund(transaction.refund)
        presenter.setParcels(transaction.parcels)
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

    private fun actionPrice() {
        presenter.requestPriceDialog(tvPrice.text.toString())
    }

    private fun actionRefund() {
        presenter.requestRefundDialog(tvRefund.text.toString())
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

    private fun actionParcels() {
        presenter.requestParcelsDialog(tvParcels.text.toString())
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

    private fun clearPrice(): Boolean {
        presenter.clearPrice()
        tvPrice.setText(R.string.system_empty_field)
        return true
    }

    private fun clearRefund(): Boolean {
        presenter.clearRefund()
        tvRefund.setText(R.string.system_empty_field)
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

    private fun clearParcels(): Boolean {
        presenter.clearParcels()
        tvParcels.setText(R.string.system_empty_field)
        return true
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

    override fun showPriceDialog(value: Double?) {
        NumberComponentDialog.newInstance(R.string.transaction_price, value) { valueParam -> presenter.setPrice(JavaUtils.NumberUtil.removeFormat(valueParam)) }.show(childFragmentManager)
    }

    override fun showRefundDialog(value: Double?) {
        NumberComponentDialog.newInstance(R.string.transaction_refund, value) { valueParam -> presenter.setRefund(JavaUtils.NumberUtil.removeFormat(valueParam)) }.show(childFragmentManager)
    }

    override fun showContentDialog(value: String?) {
        TextComponentDialog.newInstance(R.string.transaction_content, value) { valueParam -> presenter.setContent(valueParam) }.show(childFragmentManager)
    }

    override fun showParcelsDialog(value: String?) {
        TextComponentDialog.newInstance(R.string.transaction_parcels, value) { valueParam -> presenter.setParcels(valueParam) }.show(childFragmentManager)
    }

    override fun showPaymentDateDialog(date: Date) {
        JavaUtils.AndroidUtil.showDatePicker(context, date) { view, year, month, dayOfMonth ->
            presenter.setPaymentDate(JavaUtils.DateUtil.getDate(year, month + 1, dayOfMonth))
        }
    }

    override fun showPurchaseDateDialog(date: Date) {
        JavaUtils.AndroidUtil.showDatePicker(context, date) { view, year, month, dayOfMonth ->
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
                val vo = TagVO()
                vo.key = model.key
                vo.name = model.name
                presenter.setTags(vo)
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
