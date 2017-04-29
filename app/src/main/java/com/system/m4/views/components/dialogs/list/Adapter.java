package com.system.m4.views.components.dialogs.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.m4.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ItemList> list;
    private OnItemSelectedListener onItemSelectedListener;

    public Adapter(List<ItemList> list, boolean hasAddListener, OnItemSelectedListener onItemSelectedListener) {
        this.list = list;
        this.onItemSelectedListener = onItemSelectedListener;

        if (hasAddListener) {
            list.add(new ItemList("+ Add new item"));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dialog, parent, false);
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
}
