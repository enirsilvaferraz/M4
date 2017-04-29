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

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by eferraz on 14/04/17.
 * List component dialog
 */

public class ListComponentDialog extends BaseDialogFragment {

    @BindView(R.id.dialog_list_recycler)
    RecyclerView recyclerview;

    Unbinder unbinder;

    private OnItemSelectedListener onItemSelectedListener;
    private OnAddItemListenner onAddItemListenner;

    public static ListComponentDialog newInstance(@StringRes int title, List<ItemList> list, OnItemSelectedListener onItemSelectedListener) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("LIST_ARG", new ArrayList<>(list));
        bundle.putInt("TITLE", title);

        ListComponentDialog dialog = new ListComponentDialog();
        dialog.setArguments(bundle);
        dialog.setOnItemSelectedListener(onItemSelectedListener);
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

        hideDoneBtn();
        setTitle(getArguments().getInt("TITLE"));

        List<ItemList> list = (List<ItemList>) getArguments().getSerializable("LIST_ARG");

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(new Adapter(list, onAddItemListenner != null, new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                dismiss();

                if (item.getName().equals("+ Add new item")) {
                    onAddItemListenner.onItemAdded(item);
                } else {
                    onItemSelectedListener.onSelect(item);
                }
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public ListComponentDialog addOnAddItemListenner(OnAddItemListenner onAddItemListenner) {
        this.onAddItemListenner = onAddItemListenner;
        return this;
    }
}
