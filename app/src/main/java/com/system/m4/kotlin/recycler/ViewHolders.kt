package com.system.m4.kotlin.recycler

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
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

    val title: TextView = itemView.findViewById(R.id.item_title_text)

    override fun bind(vo: SubTitleVO) {
        title.text = vo.titleRes
    }
}

class ViewHolderChart internal constructor(itemView: View) : CustomViewHolder<ChartVO>(itemView) {

    val chart: CustomBarChart = itemView.findViewById(R.id.item_bar_chart)

    override fun bind(vo: ChartVO) {
        chart.bindView(vo.items)
    }
}

class ViewHolderSpace internal constructor(itemView: View) : CustomViewHolder<SpaceVO>(itemView) {

    override fun bind(vo: SpaceVO) {
        // DO NOTHING
    }
}

class ViewHolderSummary internal constructor(itemView: View) : CustomViewHolder<SummaryVO>(itemView) {

    val label: TextView = itemView.findViewById(R.id.item_summary_title)
    val value: TextView = itemView.findViewById(R.id.item_summary_value)

    override fun bind(vo: SummaryVO) {
        label.text = vo.title
        value.text = JavaUtils.NumberUtil.currencyFormat(vo.summaryValue)
    }
}

class ViewHolderTitle internal constructor(itemView: View) : CustomViewHolder<TitleVO>(itemView) {

    val title: TextView = itemView.findViewById(R.id.item_title_text)

    override fun bind(vo: TitleVO) {
        title.text = vo.titleRes
    }
}

class ViewHolderRedirectButtom internal constructor(itemView: View) : CustomViewHolder<RedirectButtomVO>(itemView) {

    var container: LinearLayout = itemView.findViewById(R.id.list_item_container)

    override fun bind(vo: RedirectButtomVO) {
        container.setOnClickListener {
            val intent = Intent(itemView.context, HomeDetailActivity::class.java)
            intent.putExtra(HomeFragment.ITEM_VIEW, vo.homeVisibility)
            intent.putExtra(HomeFragment.RELATIVE_POSITION, vo.relativePosition)
            itemView.context.startActivity(intent)
        }
    }
}

class ViewHolderTagSummary internal constructor(itemView: View) : CustomViewHolder<TagSummaryVO>(itemView) {

    var container: LinearLayout = itemView.findViewById(R.id.list_item_container)
    var label: TextView = itemView.findViewById(R.id.item_summary_title)
    var value: TextView = itemView.findViewById(R.id.item_summary_value)

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun bind(vo: TagSummaryVO) {
        label.text = if (vo.parentName != null) vo.parentName + " / " + vo.name else vo.name
        value.text = JavaUtils.NumberUtil.currencyFormat(vo.value)

        container.setOnClickListener { presenter!!.requestShowListTransaction(vo) }
    }
}

/**
 *
 */
class ViewHolderTransaction internal constructor(itemView: View) : CustomViewHolder<TransactionVO>(itemView), View.OnClickListener, View.OnLongClickListener {

    var container: LinearLayout = itemView.findViewById(R.id.list_item_container)
    var tvTag: TextView = itemView.findViewById(R.id.item_transaction_tag)
    var tvPaymentDate: TextView = itemView.findViewById(R.id.item_transaction_payment_date)
    var tvPrice: TextView = itemView.findViewById(R.id.item_transaction_price)
    var tvRefund: TextView = itemView.findViewById(R.id.item_transaction_refund)
    var tvContext: TextView = itemView.findViewById(R.id.item_transaction_context)
    var imFixed: ImageView = itemView.findViewById(R.id.item_transaction_fixed)

    private var vo: TransactionVO? = null

    override fun bind(vo: TransactionVO) {

        this.vo = vo

        tvTag.text = if (vo.tag != null) vo.tag.name else vo.paymentType.name
        tvPrice.text = JavaUtils.NumberUtil.currencyFormat(vo.price)

        if (vo.refund != 0.0) {
            tvRefund.text = JavaUtils.NumberUtil.currencyFormat(vo.refund)
            tvRefund.visibility = View.VISIBLE
        } else {
            tvRefund.visibility = View.GONE
        }

        if (vo.isOnGroup) {
            tvPaymentDate.text = JavaUtils.DateUtil.format(vo.purchaseDate, JavaUtils.DateUtil.DD)
        } else {
            tvPaymentDate.text = JavaUtils.DateUtil.format(vo.paymentDate, JavaUtils.DateUtil.DD)
        }

        if (JavaUtils.StringUtil.isEmpty(vo.content)) {
            tvContext.text = vo.tag.parentName
        } else {
            tvContext.text = vo.content
        }

        if (!TextUtils.isEmpty(vo.paymentType.color)) {
            tvPaymentDate.setTextColor(Color.parseColor(vo.paymentType.color))
        } else {
            tvPaymentDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.dafault_color))
        }

        val itemColor = if (vo.isApproved) R.color.item_default else R.color.item_pinned
        tvTag.setTextColor(itemView.context.getColor(itemColor))

        imFixed.visibility = if (vo.isFixed) View.VISIBLE else View.GONE

        if (vo.isClickable) {
            container.setOnClickListener(this)
            container.setOnLongClickListener(this)
        }
    }

    override fun onClick(v: View) {
        presenter!!.selectItem(vo!!)
    }

    override fun onLongClick(view: View): Boolean {
        showPopup()
        return true
    }

    internal fun showPopup() {
        val popupMenu = PopupMenu(itemView.context, tvPrice)
        popupMenu.inflate(R.menu.menu_transaction)

        popupMenu.menu.findItem(R.id.action_pin).isVisible = !vo!!.isFixed
        popupMenu.menu.findItem(R.id.action_unpin).isVisible = vo!!.isFixed

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_copy -> {
                    presenter!!.requestCopy(vo!!)
                    return@OnMenuItemClickListener true
                }

                R.id.action_delete -> {
                    presenter!!.requestDelete(vo!!)
                    return@OnMenuItemClickListener true
                }

                R.id.action_pin -> {
                    presenter!!.requestPin(vo!!)
                    return@OnMenuItemClickListener true
                }

                R.id.action_unpin -> {
                    presenter!!.requestUnpin(vo!!)
                    return@OnMenuItemClickListener true
                }
            }

            false
        })

        val menuHelper = MenuPopupHelper(itemView.context, popupMenu.menu as MenuBuilder, tvPrice)
        menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }
}

