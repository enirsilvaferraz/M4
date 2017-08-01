package com.system.m4.views.home;

import android.graphics.Color;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.components.CustomBarChart;
import com.system.m4.views.vos.ChartVO;
import com.system.m4.views.vos.SpaceVO;
import com.system.m4.views.vos.SubTitleVO;
import com.system.m4.views.vos.SummaryVO;
import com.system.m4.views.vos.TitleVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_SUB_TITLE = 1;
    private static final int TYPE_TRANSACTION = 2;
    private static final int TYPE_SPACE = 3;
    private static final int TYPE_SUMMARY = 4;
    private static final int TYPE_CHART = 5;

    private final HomeContract.Presenter presenter;

    private List<VOItemListInterface> list;

    HomeAdapter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
        this.list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_TITLE == viewType) {
            return new ViewHolderTitle(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false));
        } else if (TYPE_SUB_TITLE == viewType) {
            return new ViewHolderSubTitle(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_title, parent, false));
        } else if (TYPE_TRANSACTION == viewType) {
            return new ViewHolderTransaction(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_dense, parent, false));
        } else if (TYPE_SPACE == viewType) {
            return new ViewHolderSpace(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_space, parent, false));
        } else if (TYPE_SUMMARY == viewType) {
            return new ViewHolderSummary(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false));
        } else if (TYPE_CHART == viewType) {
            return new ViewHolderChart(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chart, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        VOItemListInterface item = list.get(position);
        if (item instanceof TitleVO) {
            return TYPE_TITLE;
        } else if (item instanceof SubTitleVO) {
            return TYPE_SUB_TITLE;
        } else if (item instanceof Transaction) {
            return TYPE_TRANSACTION;
        } else if (item instanceof SpaceVO) {
            return TYPE_SPACE;
        } else if (item instanceof SummaryVO) {
            return TYPE_SUMMARY;
        } else if (item instanceof ChartVO) {
            return TYPE_CHART;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (TYPE_TITLE == type) {
            ((ViewHolderTitle) holder).bind(((TitleVO) list.get(position)));
        } else if (TYPE_SUB_TITLE == type) {
            ((ViewHolderSubTitle) holder).bind(((SubTitleVO) list.get(position)));
        } else if (TYPE_TRANSACTION == type) {
            ((ViewHolderTransaction) holder).bind(((Transaction) list.get(position)));
        } else if (TYPE_SPACE == type) {
            ((ViewHolderSpace) holder).bind(((SpaceVO) list.get(position)));
        } else if (TYPE_SUMMARY == type) {
            ((ViewHolderSummary) holder).bind(((SummaryVO) list.get(position)));
        } else if (TYPE_CHART == type) {
            ((ViewHolderChart) holder).bind(((ChartVO) list.get(position)));
        }
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

    /**
     *
     */
    class ViewHolderTransaction extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.list_item_container)
        LinearLayout container;

        @BindView(R.id.item_transaction_tag)
        TextView tvTag;

        @BindView(R.id.item_transaction_payment_date)
        TextView tvPaymentDate;

        @BindView(R.id.item_transaction_price)
        TextView tvPrice;

        private Transaction item;

        ViewHolderTransaction(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Transaction item) {

            this.item = item;

            tvTag.setText(item.getTag() != null ? item.getTag().getName() : item.getPaymentType().getName());
            tvPaymentDate.setText(JavaUtils.DateUtil.format(item.getPaymentDate(), JavaUtils.DateUtil.DD));
            tvPrice.setText(JavaUtils.NumberUtil.currencyFormat(item.getPrice()));

            if (!TextUtils.isEmpty(item.getPaymentType().getColor())) {
                tvPaymentDate.setTextColor(Color.parseColor(item.getPaymentType().getColor()));
            } else {
                tvPaymentDate.setTextColor(R.color.dafault_color);
            }

            int itemColor = item.isApproved() ? R.color.item_default : R.color.item_pinned;
            tvTag.setTextColor(itemView.getContext().getColor(itemColor));

            if (item.isClickable()) {
                container.setOnClickListener(this);
                container.setOnLongClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            presenter.selectItem(item);
        }

        @Override
        public boolean onLongClick(View view) {
            showPopup();
            return true;
        }

        void showPopup() {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), tvPrice);
            popupMenu.inflate(R.menu.menu_transaction);

            popupMenu.getMenu().findItem(R.id.action_pin).setVisible(!item.isPinned());
            popupMenu.getMenu().findItem(R.id.action_unpin).setVisible(item.isPinned());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.action_copy:
                            presenter.requestCopy(ViewHolderTransaction.this.item);
                            return true;

                        case R.id.action_delete:
                            presenter.requestDelete(ViewHolderTransaction.this.item);
                            return true;

                        case R.id.action_pin:
                            presenter.pinTransaction(ViewHolderTransaction.this.item);
                            return true;

                        case R.id.action_unpin:
                            presenter.unpinTransaction(ViewHolderTransaction.this.item);
                            return true;
                    }

                    return false;
                }
            });

            MenuPopupHelper menuHelper = new MenuPopupHelper(itemView.getContext(), (MenuBuilder) popupMenu.getMenu(), tvPrice);
            menuHelper.setForceShowIcon(true);
            menuHelper.show();
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

    class ViewHolderSubTitle extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title_text)
        TextView mTitle;

        ViewHolderSubTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final SubTitleVO item) {
            mTitle.setText(item.getTitleRes());
        }
    }

    class ViewHolderSummary extends RecyclerView.ViewHolder {

        @BindView(R.id.item_summary_title)
        TextView mLabel;

        @BindView(R.id.item_summary_value)
        TextView mValue;

        ViewHolderSummary(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final SummaryVO item) {
            mLabel.setText(item.getTitle());
            mValue.setText(JavaUtils.NumberUtil.currencyFormat(item.getSummaryValue()));
        }
    }

    class ViewHolderSpace extends RecyclerView.ViewHolder {

        ViewHolderSpace(View itemView) {
            super(itemView);
        }

        public void bind(final SpaceVO item) {
            // DO NOTHING
        }
    }

    class ViewHolderChart extends RecyclerView.ViewHolder {

        @BindView(R.id.item_bar_chart)
        CustomBarChart mChart;

        ViewHolderChart(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ChartVO item) {
            mChart.setDataVO(item);
        }
    }
}
