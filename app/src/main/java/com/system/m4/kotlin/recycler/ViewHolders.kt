package com.system.m4.kotlin.recycler

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.ButterKnife
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.home.HomeDetailActivity
import com.system.m4.views.components.CustomBarChart
import com.system.m4.views.home.HomeFragment
import com.system.m4.views.vos.*

class ViewHolderSubTitle internal constructor(itemView: View) : CustomViewHolder<SubTitleVO>(itemView) {

    val mTitle: TextView = itemView.findViewById(R.id.item_title_text)

    override fun bind(vo: SubTitleVO) {
        mTitle.text = vo.titleRes
    }
}

class ViewHolderChart internal constructor(itemView: View) : CustomViewHolder<ChartVO>(itemView) {

    val mChart: CustomBarChart = itemView.findViewById(R.id.item_bar_chart)

    override fun bind(vo: ChartVO) {
        mChart.bindView(vo.items)
    }
}

class ViewHolderSpace internal constructor(itemView: View) : CustomViewHolder<SpaceVO>(itemView) {

    override fun bind(vo: SpaceVO) {
        // DO NOTHING
    }
}

class ViewHolderSummary internal constructor(itemView: View) : CustomViewHolder<SummaryVO>(itemView) {

    val mLabel: TextView = itemView.findViewById(R.id.item_summary_title)
    val mValue: TextView = itemView.findViewById(R.id.item_summary_value)

    override fun bind(vo: SummaryVO) {
        mLabel.text = vo.title
        mValue.text = JavaUtils.NumberUtil.currencyFormat(vo.summaryValue)
    }
}

class ViewHolderTitle internal constructor(itemView: View) : CustomViewHolder<TitleVO>(itemView) {

    val mTitle: TextView = itemView.findViewById(R.id.item_title_text)

    override fun bind(vo: TitleVO) {
        mTitle.text = vo.titleRes
    }
}

class ViewHolderRedirectButtom internal constructor(itemView: View) : CustomViewHolder<RedirectButtomVO>(itemView) {

    var container: LinearLayout = itemView.findViewById(R.id.list_item_container)

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun bind(vo: RedirectButtomVO) {
        container.setOnClickListener {
            val intent = Intent(itemView.context, HomeDetailActivity::class.java)
            intent.putExtra(HomeFragment.ITEM_VIEW, vo.homeVisibility)
            intent.putExtra(HomeFragment.RELATIVE_POSITION, vo.relativePosition)
            itemView.context.startActivity(intent)
        }
    }
}

