package com.system.m4.views.components.listdialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.system.m4.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final OnItemSelectedListener onItemSelectedListener;

    @BindView(R.id.transaction_manager_action_payment_date)
    View container;

    @BindView(R.id.item_list_dialog_text)
    TextView tvItem;


    public ViewHolder(View itemView, OnItemSelectedListener onItemSelectedListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void bind(final ItemList item) {
        tvItem.setText(item.getName());
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemSelectedListener.onSelect(item);
            }
        });
    }
}