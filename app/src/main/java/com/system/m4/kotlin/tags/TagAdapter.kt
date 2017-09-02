package com.system.m4.kotlin.tags

import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import com.system.m4.R

/**
 * Created by enirs on 30/08/2017.
 * Tag Adapter
 */
class TagAdapter(val onClick: TagListContract.OnAdapterClickListener) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    private val mList: ArrayList<TagModel>

    init {
        mList = arrayListOf()
    }

    override fun onBindViewHolder(holder: TagAdapter.ViewHolder?, position: Int) {
        holder?.bind(mList.get(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TagAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(list: ArrayList<TagModel>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun addOrUpdateItem(model: TagModel) {
        if (!mList.contains(model)) {
            mList.add(model)
            mList.sortBy { it.name }
            notifyItemInserted(mList.indexOf(model))
        } else {
            mList.set(mList.indexOf(model), model)
            mList.sortBy { it.name }
            notifyItemChanged(mList.indexOf(model))
        }
    }

    fun deleteItem(model: TagModel) {
        val indexOf = mList.indexOf(model)
        mList.remove(model)
        notifyItemRemoved(indexOf)
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        val mTvName = view.findViewById(R.id.item_list_dialog_text) as TextView
        val mBtMoreActions = view.findViewById(R.id.item_list_more_actions) as AppCompatImageButton

        fun bind(model: TagModel, listener: TagListContract.OnAdapterClickListener) {
            mTvName.text = model.name
            mBtMoreActions.setOnClickListener({ showPopupDialog(mBtMoreActions, model, listener) })
            view.setOnClickListener({ listener.onSelect(model) })
        }

        private fun showPopupDialog(view: View, model: TagModel, listener: TagListContract.OnAdapterClickListener) {
            val popupMenu = PopupMenu(this.itemView.context, view)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu_crud_manager, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it?.itemId) {
                    R.id.menu_crud_manager_edit -> {
                        listener.onEdit(model)
                        true
                    }
                    R.id.menu_crud_manager_delete -> {
                        listener.onDelete(model)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }
    }
}