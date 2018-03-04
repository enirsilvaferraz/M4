package com.system.m4.kotlin.tags.list

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
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.kotlin.tags.manager.TagManagerContract
import com.system.m4.kotlin.tags.manager.TagManagerDialog
import java.util.*
import javax.inject.Inject

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagListDialog : DialogFragment(), TagListContract.View, Toolbar.OnMenuItemClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgress: ProgressBar
    private lateinit var mToolbar: Toolbar

    private lateinit var mListener: TagListContract.OnSelectedListener

    @Inject
    lateinit var mPresenter: TagListContract.Presenter

    init {
        DaggerTagListComponent.builder().tagListModule(TagListModule(this)).build().inject(this)
    }

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_tag_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar = view.findViewById(R.id.dialog_toolbar)
        mToolbar.setOnMenuItemClickListener(this)
        mToolbar.inflateMenu(R.menu.menu_crud_list)

        mRecyclerView = view.findViewById(R.id.dialog_list_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = TagListAdapter(mPresenter)

        mProgress = view.findViewById(R.id.dialog_progress)

        mPresenter.init()
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
//        return dialog
//    }

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
    override fun loadTags(list: ArrayList<TagModel>) {
        (mRecyclerView.adapter as TagListAdapter).updateList(list)
    }

    override fun select(model: TagModel) {
        mListener.onSelect(model)
        dismiss()
    }

    override fun remove(model: TagModel) {
        (mRecyclerView.adapter as TagListAdapter).deleteItem(model)
    }

    override fun openManager(model: TagModel?) {
        TagManagerDialog.instance(model, object : TagManagerContract.OnCompleteListener {
            override fun onComplete(model: TagModel) {
                mPresenter.addList()
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