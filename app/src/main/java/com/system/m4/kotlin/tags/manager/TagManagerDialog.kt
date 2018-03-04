package com.system.m4.kotlin.tags.manager

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import com.system.m4.R
import com.system.m4.kotlin.infrastructure.customviews.CustomSpinnerAdapter
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.views.components.DialogFooter
import javax.inject.Inject

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagManagerDialog : DialogFragment(), TagManagerContract.View {

    private lateinit var mEtName: EditText
    private lateinit var mSpParent: AppCompatSpinner
    private lateinit var mDialogFooter: DialogFooter

    private lateinit var mListener: TagManagerContract.OnCompleteListener

    @Inject
    lateinit var mPresenter: TagManagerContract.Presenter

    init {
        TagManagerComponent.injectObject(this)
    }

    /**
     * STATIC
     */
    companion object {
        fun instance(model: TagModel?, listener: TagManagerContract.OnCompleteListener): TagManagerDialog {

            val bundle = Bundle()
            bundle.putParcelable(TagModel.TAG, model)

            val dialog = TagManagerDialog()
            dialog.mListener = listener
            dialog.arguments = bundle

            return dialog
        }

        val TAG: String = "TagManagerDialog"
    }

    /**
     * LIFECYCLE
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_tag_manager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDialogFooter = view!!.findViewById<DialogFooter>(R.id.base_dialog_container_action)
        mDialogFooter.setListener(object : DialogFooter.OnClickListener {

            override fun onDoneClick() {
                mPresenter.done(mEtName.text.toString())
            }

            override fun onCancelClick() {
                mPresenter.cancel()
            }
        })

        mEtName = view.findViewById<EditText>(R.id.dialog_description)

        mSpParent = view.findViewById<AppCompatSpinner>(R.id.dialog_spinner_tag_parent)

        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        mPresenter.init(arguments.getParcelable<TagModel>(TagModel.TAG))
    }

    /**
     * MVP
     */
    override fun fillFields(model: TagModel) {
        mEtName.setText(model.name)
    }

    override fun fillFieldParent(list: ArrayList<TagModel>, model: TagModel) {
        mSpParent.adapter = CustomSpinnerAdapter(context, android.R.layout.simple_list_item_1, list)
        mSpParent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mPresenter.configureParent(list.get(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        for ((index, it) in list.withIndex()) {
            if (it.key.equals(model.parentKey)) {
                mSpParent.setSelection(index)
                break
            }
        }
    }

    override fun returnData(model: TagModel?) {
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

    override fun enableParentSelection(enable: Boolean) {
        mSpParent.isEnabled = enable
    }
}
