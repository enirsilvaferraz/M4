package com.system.m4.kotlin.view.components

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.system.m4.R
import com.system.m4.infrastructure.Constants
import com.system.m4.labs.components.DialogFooter
import kotlinx.android.synthetic.main.dialog_text_component.*

/**
 * Created by eferraz on 14/04/17.
 * Text component dialog
 */

class TextComponentDialog : DialogFragment(), DialogFooter.OnClickListener {

    @Deprecated("Sera substituido por Activity for result")
    private lateinit var onFinishListener: OnFinishListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_text_component, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base_dialog_container_action.setListener(this)

        dialog_toolbar_title.setTitle(arguments.getInt(Constants.TITLE_BUNDLE))
        setTextContent(arguments.getString(Constants.VALUE_BUNDLE))

        if (dialog.window != null) {
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }
    }

    fun setTextContent(name: String?) {
        dialog_edit_text.setText(name)
        dialog_edit_text.requestFocus()
    }

    override fun onDoneClick() {
        dismiss()
        onFinishListener.onFinish(dialog_edit_text.text.toString())
    }

    override fun onCancelClick() {
    }

    companion object {

        fun newInstance(@StringRes title: Int, value: String?, onFinishListener: OnFinishListener): TextComponentDialog {

            val bundle = Bundle()
            bundle.putInt(Constants.TITLE_BUNDLE, title)
            bundle.putString(Constants.VALUE_BUNDLE, value)

            val dialog = TextComponentDialog()
            dialog.arguments = bundle
            dialog.onFinishListener = onFinishListener
            return dialog
        }
    }

    fun show(fragmentManager: FragmentManager) {

        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag(javaClass.simpleName)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        super.show(ft, javaClass.simpleName)
    }


    interface OnFinishListener {
        fun onFinish(value: String)
    }
}
