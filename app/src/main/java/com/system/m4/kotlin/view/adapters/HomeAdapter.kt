package com.system.m4.kotlin.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.system.m4.kotlin.view.viewholders.*
import com.system.m4.labs.chart.ChartVO
import com.system.m4.labs.vos.*
import java.util.*

/**
 *
 */
class HomeAdapter internal constructor(private val listener: Listener) : RecyclerView.Adapter<CustomViewHolder<VOItemListInterface>>() {

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
            9 -> ViewHolderAmount(parent)
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
            AmountVO::class -> 9
            else -> 0

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder<VOItemListInterface>, position: Int) {
        val vo = list[position]
        holder.onClickListener = View.OnClickListener { view -> listener.onClickVO(vo, view) }
        holder.onLongClickListener = View.OnLongClickListener { view -> listener.onLongClickVO(vo, view) }
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

    interface Listener {
        fun onClickVO(vo: VOItemListInterface, view: View)
        fun onLongClickVO(vo: VOItemListInterface, view: View): Boolean
    }
}