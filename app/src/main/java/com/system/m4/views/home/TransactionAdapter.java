package com.system.m4.views.home;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.TitleVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TRANSACTION = 1;
    private static final int TYPE_TITLE = 0;

    private final HomeContract.Presenter presenter;
    private ViewHolderTransaction markedViewHolder;
    private List<VOItemListInterface> list;

    TransactionAdapter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
        this.list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_TITLE == viewType) {
            return new ViewHolderTitle(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false));
        } else if (TYPE_TRANSACTION == viewType) {
            return new ViewHolderTransaction(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        VOItemListInterface item = list.get(position);
        if (item instanceof TransactionVO) {
            return TYPE_TRANSACTION;
        } else if (item instanceof TitleVO) {
            return TYPE_TITLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (TYPE_TITLE == type) {
            ((ViewHolderTitle) holder).bind(((TitleVO) list.get(position)));
        } else if (TYPE_TRANSACTION == type) {
            ((ViewHolderTransaction) holder).bind(((TransactionVO) list.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void markItemOff() {
        if (markedViewHolder != null) {
            markedViewHolder.markItemOff();
        }
    }

    void addFutureList(List<TransactionVO> list) {
        if (!list.isEmpty()) {
            this.list.add(new TitleVO(R.string.home_future_transactions));
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    void addCurrentList(List<TransactionVO> list) {
        if (!list.isEmpty()) {
            this.list.add(new TitleVO(R.string.home_current_transactions));
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    void clearList() {
        this.list = new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     *
     */
    class ViewHolderTransaction extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.list_item_container)
        RelativeLayout container;

        @BindView(R.id.item_transaction_tag)
        TextView tvTag;

        @BindView(R.id.item_transaction_payment_type)
        TextView tvPaymentType;

        @BindView(R.id.item_transaction_payment_date)
        TextView tvPaymentDate;

        @BindView(R.id.item_transaction_price)
        TextView tvPrice;

        private TransactionVO item;

        ViewHolderTransaction(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final TransactionVO item) {

            this.item = item;

            tvTag.setText(JavaUtils.StringUtil.formatEmpty(item.getTag().getName()));
            tvPaymentType.setText(JavaUtils.StringUtil.formatEmpty(item.getPaymentType().getName()));
            tvPaymentDate.setText(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(item.getPaymentDate(), JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)));
            tvPrice.setText(JavaUtils.StringUtil.formatEmpty(item.getPrice()));

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (markedViewHolder == null) {
                presenter.selectItem(item);
            } else {
                markedViewHolder.markItemOff();
                presenter.markItemOff();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (markedViewHolder == null) {
                markItemOn();
                presenter.markItemOn(item);
            } else if (!markedViewHolder.equals(ViewHolderTransaction.this)) {
                markedViewHolder.markItemOff();
                markItemOn();
                presenter.markItemOn(item);
            } else {
                markedViewHolder.markItemOff();
                presenter.markItemOff();
            }
            return true;
        }

        private void markItemOff() {
            container.setBackground(itemView.getContext().getDrawable(R.drawable.ripple));
            tvTag.setTypeface(Typeface.DEFAULT);
            tvPaymentType.setTypeface(Typeface.DEFAULT);
            tvPaymentDate.setTypeface(Typeface.DEFAULT);
            tvPrice.setTypeface(Typeface.DEFAULT);
            markedViewHolder = null;
        }

        private void markItemOn() {
            container.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.item_marcked));
            tvTag.setTypeface(Typeface.DEFAULT_BOLD);
            tvPaymentType.setTypeface(Typeface.DEFAULT_BOLD);
            tvPaymentDate.setTypeface(Typeface.DEFAULT_BOLD);
            tvPrice.setTypeface(Typeface.DEFAULT_BOLD);
            markedViewHolder = this;
        }
    }

    class ViewHolderTitle extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title_text)
        TextView mTitle;

        ViewHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final TitleVO item) {
            mTitle.setText(item.getTitleRes());
        }
    }
}
