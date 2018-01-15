package com.system.m4.kotlin.recycler

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.m4.R
import com.system.m4.views.home.HomeAdapter
import com.system.m4.views.vos.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

object ViewTypes {

    val TYPE_TITLE = 1
    val TYPE_SUB_TITLE = 2
    val TYPE_TRANSACTION = 3
    val TYPE_SPACE = 4
    val TYPE_SUMMARY = 5
    val TYPE_CHART = 6
    val TYPE_TAG_SUMMARY = 7
    val TYPE_REDIRECT_BUTTOM = 8
}

enum class ViewHolderEnum(val type: Int, @LayoutRes val resource: Int, val kClassVH: KClass<*>, val kClassVO: KClass<*>) {

    TITLE(
            ViewTypes.TYPE_TITLE,
            R.layout.item_title,
            ViewHolderTitle::class,
            TitleVO::class
    ),

    SUB_TITLE(
            ViewTypes.TYPE_SUB_TITLE,
            R.layout.item_sub_title,
            ViewHolderSubTitle::class,
            SubTitleVO::class
    ),

    TRANSACTION(
            ViewTypes.TYPE_TRANSACTION,
            R.layout.item_transaction_dense,
            HomeAdapter.ViewHolderTransaction::class,
            TransactionVO::class
    ),

    SPACE(
            ViewTypes.TYPE_SPACE,
            R.layout.item_space,
            ViewHolderSpace::class,
            SpaceVO::class
    ),

    SUMMARY(
            ViewTypes.TYPE_SUMMARY,
            R.layout.item_summary,
            ViewHolderSummary::class,
            SummaryVO::class
    ),

    CHART(
            ViewTypes.TYPE_CHART,
            R.layout.item_chart,
            ViewHolderChart::class,
            ChartVO::class
    ),

    TAG_SUMMARY(
            ViewTypes.TYPE_TAG_SUMMARY,
            R.layout.item_tag_summary,
            HomeAdapter.ViewHolderTagSummary::class,
            TagSummaryVO::class
    ),

    REDIRECT_BUTTOM(
            ViewTypes.TYPE_REDIRECT_BUTTOM,
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
    abstract fun bind(vo: VO)
}