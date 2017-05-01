package com.system.m4.views.components.dialogs.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.system.m4.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ListComponentAdapter extends RecyclerView.Adapter<ListComponentAdapter.ItemViewHolder> {

    private List<ItemList> list;
    private OnItemSelectedListener onItemSelectedListener;

    ListComponentAdapter(List<ItemList> list, OnItemSelectedListener onItemSelectedListener) {
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

    void addItem(ItemList item) {
        list.add(item);
        sortItens();
        notifyDataSetChanged();
    }

    private void sortItens() {
        Collections.sort(list, new Comparator<ItemList>() {
            @Override
            public int compare(ItemList o1, ItemList o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    /**
     * Created by eferraz on 29/04/17.
     * For M4
     */
    public interface OnAddItemListenner {

        void onItemAdded(String content);
    }

    /**
     *
     */
    public interface OnItemSelectedListener {

        void onSelect(ItemList item);
    }

    /**
     *
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final OnItemSelectedListener onItemSelectedListener;

        @BindView(R.id.transaction_manager_action_payment_date)
        View container;

        @BindView(R.id.item_list_dialog_text)
        TextView tvItem;


        private ItemViewHolder(View itemView, OnItemSelectedListener onItemSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemSelectedListener = onItemSelectedListener;
        }

        private void bind(final ItemList item) {
            tvItem.setText(item.getName());
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemSelectedListener.onSelect(item);
                }
            });
            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(itemView.getContext(), "Long Click not implemented!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }
}
