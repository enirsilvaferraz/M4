package com.system.m4.kotlin.tags

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.system.m4.R
import com.system.m4.views.components.DialogFooter

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagManagerDialog : DialogFragment(), TagManagerContract.View {

    private lateinit var mPresenter: TagManagerContract.Presenter
    private lateinit var mEtName: EditText
    private lateinit var mDialogFooter: DialogFooter

    companion object {
        fun instance(): TagManagerDialog {
            return TagManagerDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_tag_list, container, false)
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

        mEtName = view.findViewById(R.id.dialog_tag_description) as EditText

        mPresenter = TagManagerPresenter(this)
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeManager(model: TagModel?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}