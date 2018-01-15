package com.system.m4.views.home;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.system.m4.kotlin.recycler.CustomViewHolder;
import com.system.m4.kotlin.recycler.RecyclerConstants;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private final HomeContract.Presenter presenter;

    private List<VOItemListInterface> list;

    HomeAdapter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
        this.list = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecyclerConstants.INSTANCE.createViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return RecyclerConstants.INSTANCE.getItemViewType(list.get(position));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.setPresenter(presenter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void addCurrentList(List<VOItemListInterface> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    void clearList() {
        this.list = new ArrayList<>();
        notifyDataSetChanged();
    }
}
