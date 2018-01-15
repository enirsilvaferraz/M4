package com.system.m4.kotlin.recycler

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.m4.R
import com.system.m4.views.vos.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

enum class ViewHolderEnum(val type: Int, @LayoutRes val resource: Int, val kClassVH: KClass<*>, val kClassVO: KClass<*>) {

    TITLE(
            1,
            R.layout.item_title,
            ViewHolderTitle::class,
            TitleVO::class
    ),

    SUB_TITLE(
            2,
            R.layout.item_sub_title,
            ViewHolderSubTitle::class,
            SubTitleVO::class
    ),

    TRANSACTION(
            3,
            R.layout.item_transaction_dense,
            ViewHolderTransaction::class,
            TransactionVO::class
    ),

    SPACE(
            4,
            R.layout.item_space,
            ViewHolderSpace::class,
            SpaceVO::class
    ),

    SUMMARY(
            5,
            R.layout.item_summary,
            ViewHolderSummary::class,
            SummaryVO::class
    ),

    CHART(
            6,
            R.layout.item_chart,
            ViewHolderChart::class,
            ChartVO::class
    ),

    TAG_SUMMARY(
            7,
            R.layout.item_tag_summary,
            ViewHolderTagSummary::class,
            TagSummaryVO::class
    ),

    REDIRECT_BUTTOM(
            8,
            R.layout.item_redirect_buttom,
            ViewHolderRedirectButtom::class,
            RedirectButtomVO::class
    )
}


object RecyclerConstants {

    private fun inflate(parent: ViewGroup, vhType: ViewHolderEnum): View =
            LayoutInflater.from(parent.context).inflate(vhType.resource, parent, false)

    private fun getEnum(viewType: Int): ViewHolderEnum =
            ViewHolderEnum.values().filter { it.type == viewType }.get(0)

    fun createViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<*> {
        val vhType = getEnum(viewType)
        val view = inflate(parent, vhType)
        return vhType.kClassVH.primaryConstructor?.call(view) as CustomViewHolder<*>
    }

    fun getItemViewType(any: Any): Int =
            ViewHolderEnum.values().filter { any::class == it.kClassVO }.get(0).type
}

abstract class CustomViewHolder<VO>(val view: View) : RecyclerView.ViewHolder(view) {
    var presenter: GenericPresenter? = null
    abstract fun bind(vo: VO)
}

interface GenericPresenter {
    fun requestShowListTransaction(vo: TagSummaryVO)
    fun selectItem(vo: TransactionVO)
    fun requestCopy(vo: TransactionVO)
    fun requestDelete(vo: TransactionVO)
    fun requestPin(vo: TransactionVO)
    fun requestUnpin(vo: TransactionVO)
}