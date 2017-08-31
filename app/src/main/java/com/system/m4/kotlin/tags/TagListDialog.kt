package com.system.m4.kotlin.tags

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.m4.R
import com.system.m4.views.components.DialogToolbar
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagListDialog : DialogFragment(), TagContract.View {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: DialogToolbar

    lateinit var mPresenter: TagContract.Presenter

    companion object {
        fun instance(): TagListDialog {
            return TagListDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_tag_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view?.findViewById(R.id.dialog_list_recycler) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = TagAdapter(object : TagAdapter.OnClickListener {
            override fun onSelectItem(model: DataTag) {
                mPresenter.selectItem(model)
            }
        })

        mToolbar = view.findViewById(R.id.dialog_toolbar_title) as DialogToolbar
        mToolbar.setTitle(R.string.transaction_tag)

        mPresenter = TagPresenter(this)
        mPresenter.init()
    }

    override fun showError(error: String) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }

    override fun stopLoading() {
        Toast.makeText(context, "Stop Loading...", Toast.LENGTH_SHORT).show()
    }

    override fun loadData(list: ArrayList<DataTag>) {
        (mRecyclerView.adapter as TagAdapter).updateList(list)
    }

    override fun addData(model: DataTag) {
        (mRecyclerView.adapter as TagAdapter).addItem(model)
    }

    override fun updateData(model: DataTag) {
        (mRecyclerView.adapter as TagAdapter).updateItem(model)
    }

    override fun removeData(model: DataTag) {
        (mRecyclerView.adapter as TagAdapter).deleteItem(model)
    }

    override fun openDialogManager(model: DataTag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}