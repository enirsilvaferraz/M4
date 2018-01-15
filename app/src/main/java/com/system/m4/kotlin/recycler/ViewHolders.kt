package com.system.m4.kotlin.recycler

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.views.components.CustomBarChart
import com.system.m4.views.home.GenericPresenter
import com.system.m4.views.vos.*

abstract class CustomViewHolder<VO>(val view: View) : RecyclerView.ViewHolder(view) {

    @Deprecated(message = "Sera substituido pelo onClickListener e pelo onLongClickListener")
    var presenter: GenericPresenter? = null

    lateinit var onClickListener: View.OnClickListener
    lateinit var onLongClickListener: View.OnLongClickListener
    abstract fun bind(vo: VO)
}

class ViewHolderSubTitle internal constructor(itemView: View) : CustomViewHolder<SubTitleVO>(itemView) {

    private val title: TextView = itemView.findViewById(R.id.item_title_text)

    override fun bind(vo: SubTitleVO) {
        title.text = vo.titleRes
    }
}

class ViewHolderChart internal constructor(itemView: View) : CustomViewHolder<ChartVO>(itemView) {

    private val chart: CustomBarChart = itemView.findViewById(R.id.item_bar_chart)

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

    private val label: TextView = itemView.findViewById(R.id.item_summary_title)
    private val value: TextView = itemView.findViewById(R.id.item_summary_value)

    override fun bind(vo: SummaryVO) {
        label.text = vo.title
        value.text = JavaUtils.NumberUtil.currencyFormat(vo.summaryValue)
    }
}

class ViewHolderTitle internal constructor(itemView: View) : CustomViewHolder<TitleVO>(itemView) {

    private val title: TextView = itemView.findViewById(R.id.item_title_text)

    override fun bind(vo: TitleVO) {
        title.text = vo.titleRes
    }
}

class ViewHolderRedirectButtom internal constructor(itemView: View) : CustomViewHolder<RedirectButtomVO>(itemView) {

    private val container: LinearLayout = itemView.findViewById(R.id.list_item_container)

    override fun bind(vo: RedirectButtomVO) {
        container.setOnClickListener(onClickListener)
    }
}

class ViewHolderTagSummary internal constructor(itemView: View) : CustomViewHolder<TagSummaryVO>(itemView) {

    private val container: LinearLayout = itemView.findViewById(R.id.list_item_container)
    private val label: TextView = itemView.findViewById(R.id.item_summary_title)
    private val value: TextView = itemView.findViewById(R.id.item_summary_value)

    override fun bind(vo: TagSummaryVO) {
        label.text = if (vo.parentName != null) vo.parentName + " / " + vo.name else vo.name
        value.text = JavaUtils.NumberUtil.currencyFormat(vo.value)
        container.setOnClickListener(onClickListener)
    }
}

/**
 *
 */
class ViewHolderTransaction internal constructor(itemView: View) : CustomViewHolder<TransactionVO>(itemView), View.OnLongClickListener {

    private val container: LinearLayout = itemView.findViewById(R.id.list_item_container)
    private val tvTag: TextView = itemView.findViewById(R.id.item_transaction_tag)
    private val tvPaymentDate: TextView = itemView.findViewById(R.id.item_transaction_payment_date)
    private val tvPrice: TextView = itemView.findViewById(R.id.item_transaction_price)
    private val tvRefund: TextView = itemView.findViewById(R.id.item_transaction_refund)
    private val tvContext: TextView = itemView.findViewById(R.id.item_transaction_context)
    private val imFixed: ImageView = itemView.findViewById(R.id.item_transaction_fixed)

    private var vo: TransactionVO? = null

    override fun bind(vo: TransactionVO) {

        this.vo = vo

        tvTag.text = if (vo.tag != null) vo.tag.name else vo.paymentType.name
        tvPrice.text = JavaUtils.NumberUtil.currencyFormat(vo.price)

        tvRefund.text = JavaUtils.NumberUtil.currencyFormat(vo.refund)
        tvRefund.visibility = if (vo.refund != 0.0) View.VISIBLE else View.GONE

        val date = if (vo.isOnGroup) vo.purchaseDate else vo.paymentDate
        tvPaymentDate.text = JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD)

        tvContext.text = if (JavaUtils.StringUtil.isEmpty(vo.content)) vo.tag.parentName else vo.content

        val empty = TextUtils.isEmpty(vo.paymentType.color)
        tvPaymentDate.setTextColor(if (!empty) Color.parseColor(vo.paymentType.color) else
            ContextCompat.getColor(itemView.context, R.color.dafault_color))

        val itemColor = if (vo.isApproved) R.color.item_default else R.color.item_pinned
        tvTag.setTextColor(itemView.context.getColor(itemColor))

        imFixed.visibility = if (vo.isFixed) View.VISIBLE else View.GONE

        container.setOnClickListener(if (vo.isClickable) onClickListener else null)
        container.setOnLongClickListener(if (vo.isClickable) this else null)
    }

    override fun onLongClick(view: View): Boolean {
        showPopup()
        return true
    }

    internal fun showPopup() {
        val popupMenu = PopupMenu(itemView.context, itemView)
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