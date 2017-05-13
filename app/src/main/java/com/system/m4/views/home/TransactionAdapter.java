package com.system.m4.views.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.TransactionVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<TransactionVO> list;
    private OnItemSelectedListener onItemSelectedListener;

    TransactionAdapter(List<TransactionVO> list, OnItemSelectedListener onItemSelectedListener) {
        this.list = list;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     *
     */
    interface OnItemSelectedListener {

        void onSelect(TransactionVO item);
    }


    /**
     *
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_transaction_tag)
        TextView tvTag;

        @BindView(R.id.item_transaction_payment_type)
        TextView tvPaymentType;

        @BindView(R.id.item_transaction_payment_date)
        TextView tvPaymentDate;

        @BindView(R.id.item_transaction_price)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final TransactionVO item) {

            tvTag.setText(JavaUtils.StringUtil.formatEmpty(item.getTag().getName()));
            tvPaymentType.setText(JavaUtils.StringUtil.formatEmpty(item.getPaymentType().getName()));
            tvPaymentDate.setText(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(item.getPaymentDate(), JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)));
            tvPrice.setText(JavaUtils.StringUtil.formatEmpty(item.getPrice()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelectedListener.onSelect(item);
                }
            });
        }
    }
}
