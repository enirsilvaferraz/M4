package com.system.m4.kotlin.infrastructure

/**
 * Created by enirs on 02/10/2017.
 */

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Toast
import com.system.m4.R
import com.system.m4.views.components.DialogFooter
import com.system.m4.views.components.DialogToolbar
import com.system.m4.views.vos.VOInterface

/**
 * Created by Enir on 20/04/2017.
 * Base dialog
 */

abstract class BaseDialogFragment : DialogFragment(), DialogFooter.OnClickListener, DialogToolbar.OnClickListener {

    lateinit var mToolbar: DialogToolbar
    lateinit var mFooter: DialogFooter

    @Deprecated("")
    var onFinishListener: OnFinishListener? = null

    var dialogListener: DialogListener? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar = view?.findViewById(R.id.dialog_toolbar_title) as DialogToolbar
        mFooter = view.findViewById(R.id.base_dialog_container_action) as DialogFooter

        mToolbar.setListener(this)
        mFooter.setListener(this)
    }

    protected fun hideFooter() {
        mFooter.visibility = View.GONE
    }

    protected fun setTitle(@StringRes titleId: Int) {
        mToolbar.setTitle(titleId)
    }

    protected fun setTitle(titleString: String) {
        mToolbar.setTitle(titleString)
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

    override fun onAddClick() {
        Toast.makeText(context, "Not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    override fun onEditClick() {
        Toast.makeText(context, "Not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick() {
        Toast.makeText(context, "Not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    override fun onDoneClick() {
        Toast.makeText(context, "Not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    override fun onCancelClick() {
        dismiss()
    }

    interface OnFinishListener {
        fun onFinish(value: String)
    }

    interface DialogListener {
        fun onFinish(vo: VOInterface<*>)
    }

    companion object {

        val TITLE_BUNDLE = "TITLE_BUNDLE"
    }
}
