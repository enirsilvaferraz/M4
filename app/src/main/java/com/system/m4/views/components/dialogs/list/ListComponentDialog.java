package com.system.m4.views.components.dialogs.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.TextComponentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by eferraz on 14/04/17.
 * List component dialog
 */

public class ListComponentDialog extends BaseDialogFragment {

    @BindView(R.id.dialog_list_recycler)
    RecyclerView recyclerview;

    @BindView(R.id.dialog_add_item)
    ImageButton btnAddItem;

    Unbinder unbinder;

    private ListComponentAdapter.OnItemSelectedListener onItemSelectedListener;
    private ListComponentAdapter.OnAddItemListenner onAddItemListenner;

    private ListComponentAdapter mAdapter;

    public static ListComponentDialog newInstance(@StringRes int title, List<ItemList> list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_BUNDLE, new ArrayList<>(list));
        bundle.putInt(TITLE_BUNDLE, title);

        ListComponentDialog dialog = new ListComponentDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list_component, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showAddButtom();
        hideDoneBtn();

        setTitle(getArguments().getInt(TITLE_BUNDLE));

        List<ItemList> list = getArguments().getParcelableArrayList(LIST_BUNDLE);
        mAdapter = new ListComponentAdapter(list, new ListComponentAdapter.OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                dismiss();
                onItemSelectedListener.onSelect(item);
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }

    private void showAddButtom() {
        if (onAddItemListenner != null) {
            btnAddItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.dialog_add_item)
    public void addItemAction() {
        TextComponentDialog.newInstance(R.string.transaction_tag, null, new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {

                ItemList item = new ItemList(value);
                mAdapter.addItem(item);

                onAddItemListenner.onItemAdded(value);
            }
        }).show(getChildFragmentManager(), TextComponentDialog.TAG);
    }

    public ListComponentDialog addOnItemSelectedListener(ListComponentAdapter.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        return this;
    }

    public ListComponentDialog addOnAddItemListenner(ListComponentAdapter.OnAddItemListenner onAddItemListenner) {
        this.onAddItemListenner = onAddItemListenner;
        return this;
    }
}
