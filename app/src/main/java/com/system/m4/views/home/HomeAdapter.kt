package com.system.m4.views.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.m4.kotlin.recycler.CustomViewHolder
import com.system.m4.kotlin.recycler.ViewHolderEnum
import com.system.m4.views.vos.TagSummaryVO
import com.system.m4.views.vos.TransactionVO
import com.system.m4.views.vos.VOItemListInterface
import java.util.*
import kotlin.reflect.full.primaryConstructor

/**
 *
 */
class HomeAdapter internal constructor(private val presenter: HomeContract.Presenter) : RecyclerView.Adapter<CustomViewHolder<VOItemListInterface>>() {

    private val list: MutableList<VOItemListInterface> = ArrayList()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<VOItemListInterface> {
        val viewHolderEnum = ViewHolderEnum.values().filter { it.type == viewType }.get(0)
        val view = LayoutInflater.from(parent.context).inflate(viewHolderEnum.resource, parent, false)
        return viewHolderEnum.kClassVH.primaryConstructor?.call(view) as CustomViewHolder<VOItemListInterface>
    }

    override fun getItemViewType(position: Int): Int {
        return ViewHolderEnum.values().filter { list[position]::class == it.kClassVO }.get(0).type
    }

    override fun onBindViewHolder(holder: CustomViewHolder<VOItemListInterface>, position: Int) {

        val vo = list[position]

        holder.onClickListener = View.OnClickListener { presenter.onClickVO(vo) }
        holder.onLongClickListener = View.OnLongClickListener { presenter.onLongClickVO(vo) }

        holder.bind(vo)
        holder.presenter = presenter
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addCurrentList(list: List<VOItemListInterface>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        this.list.clear()
        notifyDataSetChanged()
    }
}

@Deprecated("Sera substituido")
interface GenericPresenter {
    fun requestShowListTransaction(vo: TagSummaryVO)
    fun selectItem(vo: TransactionVO)
    fun requestCopy(vo: TransactionVO)
    fun requestDelete(vo: TransactionVO)
    fun requestPin(vo: TransactionVO)
    fun requestUnpin(vo: TransactionVO)
}
