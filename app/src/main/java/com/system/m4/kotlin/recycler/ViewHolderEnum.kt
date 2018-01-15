package com.system.m4.kotlin.recycler

import android.support.annotation.LayoutRes
import com.system.m4.R
import com.system.m4.views.vos.*
import kotlin.reflect.KClass

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
    );
}