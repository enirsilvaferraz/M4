package com.system.m4.kotlin.tags

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.system.m4.R
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagListDialog : DialogFragment(), TagListContract.View, Toolbar.OnMenuItemClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgress: ProgressBar
    private lateinit var mToolbar: Toolbar

    private lateinit var mListener: TagListContract.OnSelectedListener
    private lateinit var mPresenter: TagListContract.Presenter

    /**
     * STATIC
     */
    companion object {
        fun instance(listener: TagListContract.OnSelectedListener): TagListDialog {
            val dialog = TagListDialog()
            dialog.mListener = listener
            return dialog
        }
    }

    /**
     * LIFECYCLE
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_tag_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar = view?.findViewById(R.id.dialog_toolbar) as Toolbar
        mToolbar.setOnMenuItemClickListener(this)
        mToolbar.inflateMenu(R.menu.menu_crud_list)

        mRecyclerView = view.findViewById(R.id.dialog_list_recycler) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = TagAdapter(object : TagListContract.OnAdapterClickListener {

            override fun onSelect(model: TagModel) {
                mPresenter.select(model)
            }

            override fun onEdit(model: TagModel) {
                mPresenter.edit(model)
            }

            override fun onDelete(model: TagModel) {
                mPresenter.delete(model)
            }
        })

        mProgress = view.findViewById(R.id.dialog_progress) as ProgressBar

        mPresenter = TagListPresenter(this)
        mPresenter.load()
    }

    /**
     * LISTENERS
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId!! == R.id.menu_crud_list_add_new) {
            mPresenter.create()
        }
        return true
    }

    /**
     * MVP
     */
    override fun load(list: ArrayList<TagModel>) {
        (mRecyclerView.adapter as TagAdapter).updateList(list)
    }

    override fun select(model: TagModel) {
        mListener.onSelect(model)
        dismiss()
    }

    override fun remove(model: TagModel) {
        (mRecyclerView.adapter as TagAdapter).deleteItem(model)
    }

    override fun openManager(model: TagModel?) {
        TagManagerDialog.instance(model, object : TagManagerContract.OnCompleteListener {
            override fun onComplete(model: TagModel) {
                (mRecyclerView.adapter as TagAdapter).addOrUpdateItem(model)
            }
        }).show(fragmentManager, TagManagerDialog.TAG)
    }

    override fun showLoading() {
        mProgress.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        mProgress.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}