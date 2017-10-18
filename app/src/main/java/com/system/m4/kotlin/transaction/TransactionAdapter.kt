package com.system.m4.kotlin.transaction

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.views.vos.TransactionVO

/**
 * Created by enirs on 11/10/2017.
 * Adapter list
 */
class TransactionAdapter(val mList: ArrayList<TransactionVO>, val listener: OnClickListener) : RecyclerView.Adapter<TransactionAdapter.ViewHolderTransaction>() {

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolderTransaction?, position: Int) {
        holder?.bind(mList.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionAdapter.ViewHolderTransaction {
        return TransactionAdapter.ViewHolderTransaction(LayoutInflater.from(parent?.context).inflate(R.layout.item_transaction_dense, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    /**
     *
     */
    class ViewHolderTransaction(itemView: View, val listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {

        lateinit var container: LinearLayout
        lateinit var tvTag: TextView
        lateinit var tvPaymentDate: TextView
        lateinit var tvPrice: TextView
        lateinit var tvContext: TextView
        lateinit var imFixed: ImageView

        fun bind(item: TransactionVO) {

            container = itemView.findViewById(R.id.list_item_container)
            tvTag = itemView.findViewById(R.id.item_transaction_tag)
            tvPaymentDate = itemView.findViewById(R.id.item_transaction_payment_date)
            tvPrice = itemView.findViewById(R.id.item_transaction_price)
            tvContext = itemView.findViewById(R.id.item_transaction_context)
            imFixed = itemView.findViewById(R.id.item_transaction_fixed)

            tvTag.text = if (item.tag != null) item.tag.name else item.paymentType.name
            tvPaymentDate.text = JavaUtils.DateUtil.format(item.paymentDate, JavaUtils.DateUtil.DD)
            tvPrice.text = JavaUtils.NumberUtil.currencyFormat(item.price)

            if (JavaUtils.StringUtil.isEmpty(item.content)) {
                tvContext.text = item.tag.parentName
            } else {
                tvContext.text = item.content
            }

            if (!TextUtils.isEmpty(item.paymentType.color)) {
                tvPaymentDate.setTextColor(Color.parseColor(item.paymentType.color))
            } else {
                tvPaymentDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.dafault_color))
            }

            val itemColor = if (item.isApproved) R.color.item_default else R.color.item_pinned
            tvTag.setTextColor(itemView.context.getColor(itemColor))

            imFixed.visibility = if (item.isFixed) View.VISIBLE else View.GONE

            if (item.isClickable) {
                container.setOnClickListener({ listener.onClick(item) })
            }
        }
    }

    interface OnClickListener {
        fun onClick(vo: TransactionVO)
    }
}