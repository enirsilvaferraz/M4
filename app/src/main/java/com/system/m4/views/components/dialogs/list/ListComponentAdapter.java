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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
class ListComponentAdapter extends RecyclerView.Adapter<ListComponentAdapter.ItemViewHolder> {

    private final ListComponentContract.Presenter presenter;
    private List<VOInterface> list;
    private ListComponentAdapter.ItemViewHolder markedViewHolder;

    ListComponentAdapter(List<VOInterface> list, ListComponentContract.Presenter presenter) {
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dialog, parent, false);
        return new ItemViewHolder(view);
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
        list.add(item);
        notifyItemInserted(list.indexOf(item));
    }

    void changeItem(VOInterface vo) {
        int indexOf = list.indexOf(vo);
        list.remove(indexOf);
        notifyItemRemoved(indexOf);
        addItem(vo);
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

    void markItemOff() {
        if (markedViewHolder != null) {
            markedViewHolder.markItemOff();
        }
    }

    /**
     *
     */
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.transaction_manager_action_payment_date)
        View container;

        @BindView(R.id.item_list_dialog_text)
        TextView tvItem;

        private VOInterface item;

        private ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        private void bind(final VOInterface item) {
            tvItem.setText(item.getName());
            this.item = item;
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
            } else if (!markedViewHolder.equals(ItemViewHolder.this)) {
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
            tvItem.setTypeface(Typeface.DEFAULT);
            markedViewHolder = null;
        }

        private void markItemOn() {
            container.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.item_marcked));
            tvItem.setTypeface(Typeface.DEFAULT_BOLD);
            markedViewHolder = this;
        }
    }
}
