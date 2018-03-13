package com.system.m4.kotlin.view.components

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.system.m4.R
import com.system.m4.infrastructure.Constants
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.labs.components.DialogFooter
import kotlinx.android.synthetic.main.dialog_number_component.*

/**
 * Created by eferraz on 14/04/17.
 * Number compoenent dialog
 */

class NumberComponentDialog : DialogFragment(), DialogFooter.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_number_component, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base_dialog_container_action.setListener(this)

        setTitle(arguments.getInt("TITLE"))

        dialog_edit_number.defaultHintEnabled = false
        dialog_edit_number.requestFocus()

        val value = if (arguments.containsKey("VALUE")) arguments.getDouble("VALUE") else 0.0
        dialog_edit_number.setText(JavaUtils.NumberUtil.currencyFormat(value))

        if (dialog.window != null) {
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }
    }

    protected fun setTitle(@StringRes titleId: Int) {
        dialog_toolbar_title.setTitle(titleId)
    }

    override fun onDoneClick() {
        dismiss()

        val intent = Intent()
        intent.putExtra(Constants.VALUE_BUNDLE, dialog_edit_number.text.toString())
        targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }

    override fun onCancelClick() {}

    companion object {

        fun newInstance(@StringRes title: Int, value: Double?, target: Fragment, requestCode: Int): NumberComponentDialog {

            val bundle = Bundle()
            bundle.putInt("TITLE", title)

            if (value != null) {
                bundle.putDouble("VALUE", value)
            }

            val dialog = NumberComponentDialog()
            dialog.setTargetFragment(target, requestCode)
            dialog.arguments = bundle
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
}