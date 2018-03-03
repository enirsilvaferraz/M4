package com.system.m4.kotlin.paymenttype

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.system.m4.R
import com.system.m4.views.components.DialogFooter

/**
 * Created by enirs on 30/08/2017.
 * Activity from PaymentType
 */
class PaymentTypeManagerDialog : DialogFragment(), PaymentTypeManagerContract.View {

    private lateinit var mPresenter: PaymentTypeManagerContract.Presenter
    private lateinit var mEtName: EditText
    private lateinit var mDialogFooter: DialogFooter

    private lateinit var mListener: PaymentTypeManagerContract.OnCompleteListener

    /**
     * STATIC
     */
    companion object {

        fun instance(model: PaymentTypeModel?, listener: PaymentTypeManagerContract.OnCompleteListener): PaymentTypeManagerDialog {

            val bundle = Bundle()
            bundle.putParcelable(PaymentTypeModel.TAG, model)

            val dialog = PaymentTypeManagerDialog()
            dialog.mListener = listener
            dialog.arguments = bundle

            return dialog
        }

        const val TAG: String = "PaymentTypeManagerDialog"
    }

    /**
     * LIFECYCLE
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_paymenttype_manager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDialogFooter = view!!.findViewById(R.id.base_dialog_container_action)
        mDialogFooter.setListener(object : DialogFooter.OnClickListener {

            override fun onDoneClick() {
                mPresenter.done(mEtName.text.toString())
            }

            override fun onCancelClick() {
                mPresenter.cancel()
            }
        })

        mEtName = view.findViewById(R.id.dialog_description)

        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        mPresenter = PaymentTypeManagerPresenter(this)
        mPresenter.init(arguments.getParcelable(PaymentTypeModel.TAG))
    }

    /**
     * MVP
     */
    override fun fillFields(model: PaymentTypeModel) {
        mEtName.setText(model.name)
    }

    override fun returnData(model: PaymentTypeModel?) {
        model?.let { mListener.onComplete(it) }
        dismiss()
    }

    override fun showLoading() {
        mDialogFooter.showLoading()
    }

    override fun stopLoading() {
        mDialogFooter.hideLoading()
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}