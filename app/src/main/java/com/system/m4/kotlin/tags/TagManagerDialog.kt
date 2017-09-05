package com.system.m4.kotlin.tags

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.system.m4.R
import com.system.m4.views.components.DialogFooter

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagManagerDialog : DialogFragment(), TagManagerContract.View {

    private lateinit var mPresenter: TagManagerContract.Presenter
    private lateinit var mEtName: EditText
    private lateinit var mAcParent: AppCompatAutoCompleteTextView
    private lateinit var mDialogFooter: DialogFooter

    private lateinit var mListener: TagManagerContract.OnCompleteListener

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

        mDialogFooter = view?.findViewById(R.id.base_dialog_container_action) as DialogFooter
        mDialogFooter.setListener(object : DialogFooter.OnClickListener {

            override fun onDoneClick() {
                mPresenter.done(mEtName.text.toString())
            }

            override fun onCancelClick() {
                mPresenter.cancel()
            }
        })

        mEtName = view.findViewById(R.id.dialog_description) as EditText

        mAcParent = view.findViewById(R.id.dialog_autocomplete_tag_parent) as AppCompatAutoCompleteTextView

        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        mPresenter = TagManagerPresenter(this)
        mPresenter.init(arguments.getParcelable<TagModel>(TagModel.TAG))
    }

    /**
     * MVP
     */
    override fun fillFields(model: TagModel) {
        mEtName.setText(model.name)
    }

    override fun configureFields(list: ArrayList<String>) {
        val adapter = ArrayAdapter<String> (context, android.R.layout.simple_list_item_1, list)
        mAcParent.setAdapter(adapter)
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


}