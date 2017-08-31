package com.system.m4.kotlin.tags

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.system.m4.R

/**
 * Created by enirs on 30/08/2017.
 * Tag Adapter
 */
class TagAdapter(val onClick: TagAdapter.OnClickListener) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    private val mList: ArrayList<DataTag>

    init {
        mList = arrayListOf()
    }

    override fun onBindViewHolder(holder: TagAdapter.ViewHolder?, position: Int) {
        holder?.mTvName?.setText(mList.get(position).name)
        holder?.view!!.setOnClickListener({ onClick.onSelectItem(mList.get(position)) })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TagAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(list: ArrayList<DataTag>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(model: DataTag) {
        mList.add(model)
        mList.sortBy { it.name }
        notifyItemInserted(mList.indexOf(model))
    }

    fun updateItem(model: DataTag) {
        mList.set(mList.indexOf(model), model)
        mList.sortBy { it.name }
        notifyItemChanged(mList.indexOf(model))
    }

    fun deleteItem(model: DataTag) {
        val indexOf = mList.indexOf(model)
        mList.remove(model)
        notifyItemRemoved(indexOf)
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val mTvName: TextView = view.findViewById(R.id.item_list_dialog_text) as TextView
    }

    interface OnClickListener {
        fun onSelectItem(model: DataTag)
    }
}