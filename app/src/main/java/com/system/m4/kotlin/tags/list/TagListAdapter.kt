package com.system.m4.kotlin.tags.list

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import com.system.m4.R
import com.system.m4.kotlin.tags.TagModel

/**
 * Created by enirs on 30/08/2017.
 * Tag Adapter
 */
class TagListAdapter(val onClick: TagListContract.OnAdapterClickListener) : RecyclerView.Adapter<TagListAdapter.ViewHolder>() {

    private val mList: ArrayList<TagModel>

    init {
        mList = arrayListOf()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(mList.get(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
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

        val mTvName = view.findViewById<TextView>(R.id.item_list_dialog_text)
        val mBtMoreActions = view.findViewById<ImageButton>(R.id.item_list_more_actions)
        val mContainer = view.findViewById<ViewGroup>(R.id.list_item_container)

        fun bind(model: TagModel, listener: TagListContract.OnAdapterClickListener) {
            mTvName.text = model.name
            mBtMoreActions.setOnClickListener({ showPopupDialog(mBtMoreActions, model, listener) })

            if (model.parentKey.isNullOrBlank()) {
                mContainer.isEnabled = false
                mContainer.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.md_grey_300))
            } else {
                mContainer.isEnabled = true
                mContainer.setBackgroundResource(R.drawable.ripple)
                mContainer.setOnClickListener({ listener.onSelect(model) })
            }
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