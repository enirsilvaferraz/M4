package com.system.m4.views.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.system.m4.kotlin.recycler.*
import com.system.m4.views.vos.*
import java.util.*

/**
 *
 */
class HomeAdapter internal constructor(private val presenter: HomeContract.Presenter) : RecyclerView.Adapter<CustomViewHolder<VOItemListInterface>>() {

    private val list: MutableList<VOItemListInterface> = ArrayList()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<VOItemListInterface> {

        return when (viewType) {

            1 -> ViewHolderTitle(parent)
            2 -> ViewHolderSubTitle(parent)
            3 -> ViewHolderSummary(parent)
            4 -> ViewHolderTagSummary(parent)
            5 -> ViewHolderTransaction(parent)
            6 -> ViewHolderRedirectButtom(parent)
            7 -> ViewHolderSpace(parent)
            8 -> ViewHolderChart(parent)
            else -> null

        } as CustomViewHolder<VOItemListInterface>
    }

    override fun getItemViewType(position: Int): Int {

        return when (list[position]::class) {

            TitleVO::class -> 1
            SubTitleVO::class -> 2
            SummaryVO::class -> 3
            TagSummaryVO::class -> 4
            TransactionVO::class -> 5
            RedirectButtomVO::class -> 6
            SpaceVO::class -> 7
            ChartVO::class -> 8
            else -> 0

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder<VOItemListInterface>, position: Int) {
        val vo = list[position]
        holder.onClickListener = View.OnClickListener { presenter.onClickVO(vo) }
        holder.onLongClickListener = View.OnLongClickListener { view -> presenter.onLongClickVO(vo, view) }
        holder.bind(vo)
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
