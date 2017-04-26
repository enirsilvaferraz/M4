package com.system.m4.views.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.m4.R;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

/**
 *
 */
class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<ItemVO> list;
    private OnItemSelectedListener onItemSelectedListener;

    TransactionAdapter(List<ItemVO> list, OnItemSelectedListener onItemSelectedListener) {
        this.list = list;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view, onItemSelectedListener);
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

        void onSelect(ItemVO item);
    }

    /**
     *
     */
    static class ItemVO implements Serializable {

        @Getter
        private String paymentDate;

        @Getter
        private String purchaseDate;

        @Getter
        private String value;

        @Getter
        private String tags;

        @Getter
        private String paymentType;

        @Getter
        private String content;

        public ItemVO(String paymentDate, String purchaseDate, String value, String tags, String paymentType, String content) {
            this.paymentDate = paymentDate;
            this.purchaseDate = purchaseDate;
            this.value = value;
            this.tags = tags;
            this.paymentType = paymentType;
            this.content = content;
        }
    }

    /**
     *
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final OnItemSelectedListener onItemSelectedListener;


        public ViewHolder(View itemView, OnItemSelectedListener onItemSelectedListener) {
            super(itemView);
            this.onItemSelectedListener = onItemSelectedListener;
        }

        public void bind(final ItemVO item) {

        }
    }
}
