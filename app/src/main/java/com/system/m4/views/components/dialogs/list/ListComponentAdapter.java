package com.system.m4.views.components.dialogs.list;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.m4.R;
import com.system.m4.views.vos.VOInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
class ListComponentAdapter extends RecyclerView.Adapter<ListComponentAdapter.ItemViewHolder> {

    private List<VOInterface> list;
    private OnItemSelectedListener onItemSelectedListener;
    private ListComponentAdapter.ItemViewHolder markedViewHolder;

    ListComponentAdapter(List<VOInterface> list, OnItemSelectedListener onItemSelectedListener) {
        this.list = list;
        this.onItemSelectedListener = onItemSelectedListener;
        sortItens();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dialog, parent, false);
        return new ItemViewHolder(view, onItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void addItem(VOInterface item) {

        if (!list.contains(item)) {
            list.add(item);
        } else {
            list.remove(item);
            list.add(item);
        }

        sortItens();
        notifyDataSetChanged();
    }

    private void sortItens() {
        Collections.sort(list, new Comparator<VOInterface>() {
            @Override
            public int compare(VOInterface o1, VOInterface o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    VOInterface getMarkedItem() {
        return markedViewHolder.getItemList();
    }

    void markItemOff() {
        if (markedViewHolder != null) {
            markedViewHolder.markItemOff();
        }
    }

    void removeItem(VOInterface item) {
        int indexOf = list.indexOf(item);
        list.remove(item);
        notifyItemRemoved(indexOf);
    }

    void addList(List<VOInterface> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     *
     */
    interface OnItemSelectedListener {

        void onSelect(VOInterface item);

        void onMarkOn(VOInterface item);

        void onMarkOff();
    }

    /**
     *
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final OnItemSelectedListener onItemSelectedListener;

        @BindView(R.id.transaction_manager_action_payment_date)
        View container;
        @BindView(R.id.item_list_dialog_text)
        TextView tvItem;

        private VOInterface itemList;

        private ItemViewHolder(View itemView, OnItemSelectedListener onItemSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemSelectedListener = onItemSelectedListener;
        }

        private void bind(final VOInterface item) {

            itemList = item;

            tvItem.setText(item.getName());

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (markedViewHolder == null) {
                        onItemSelectedListener.onSelect(item);
                    } else {
                        markedViewHolder.markItemOff();
                        onItemSelectedListener.onMarkOff();
                    }
                }
            });

            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (markedViewHolder == null) {
                        markItemOn();
                        onItemSelectedListener.onMarkOn(item);
                    } else if (!markedViewHolder.equals(ItemViewHolder.this)) {
                        markedViewHolder.markItemOff();
                        markItemOn();
                        onItemSelectedListener.onMarkOn(item);
                    } else {
                        markedViewHolder.markItemOff();
                        onItemSelectedListener.onMarkOff();
                    }
                    return true;
                }
            });
        }

        private void markItemOff() {
            container.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            tvItem.setTypeface(Typeface.DEFAULT);
            markedViewHolder = null;
        }

        private void markItemOn() {
            container.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.item_marcked));
            tvItem.setTypeface(Typeface.DEFAULT_BOLD);
            markedViewHolder = this;
        }

        VOInterface getItemList() {
            return itemList;
        }
    }
}
