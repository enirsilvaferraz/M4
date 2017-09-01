package com.system.m4.kotlin.tags

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.system.m4.R
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagListDialog : DialogFragment(), TagListContract.View {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgress: ProgressBar

    lateinit var mPresenter: TagListContract.Presenter

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
            override fun onSelectItem(model: TagModel) {
                mPresenter.selectItem(model)
            }
        })

        mProgress = view.findViewById(R.id.tag_dialog_progress) as ProgressBar

        mPresenter = TagListPresenter(this)
        mPresenter.init()
    }

    override fun showError(error: String) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        mProgress.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        mProgress.visibility = View.GONE
    }

    override fun loadData(list: ArrayList<TagModel>) {
        (mRecyclerView.adapter as TagAdapter).updateList(list)
    }

    override fun addData(model: TagModel) {
        (mRecyclerView.adapter as TagAdapter).addItem(model)
    }

    override fun updateData(model: TagModel) {
        (mRecyclerView.adapter as TagAdapter).updateItem(model)
    }

    override fun removeData(model: TagModel) {
        (mRecyclerView.adapter as TagAdapter).deleteItem(model)
    }

    override fun openDialogManager(model: TagModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}