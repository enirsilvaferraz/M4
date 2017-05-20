package com.system.m4.views.components.dialogs.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.system.m4.views.components.DialogToolbar;
import com.system.m4.views.components.dialogs.TextComponentDialog;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by eferraz on 14/04/17.
 * List component dialog
 */

public class ListComponentDialog extends BaseDialogFragment implements DialogToolbar.OnClickListener {

    @BindView(R.id.dialog_list_recycler)
    RecyclerView recyclerview;

    Unbinder unbinder;

    private OnItemListenner onItemListenner;

    private ListComponentAdapter mAdapter;

    public static ListComponentDialog newInstance(@StringRes int title) {

        Bundle bundle = new Bundle();
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

        hideDoneBtn();

        mToolbar.setTitle(getArguments().getInt(TITLE_BUNDLE));
        mToolbar.configureCreateMode();
        mToolbar.setOnClickListener(this);

        List<VOInterface> list = new ArrayList<>();
        mAdapter = new ListComponentAdapter(list, new ListComponentAdapter.OnItemSelectedListener() {

            @Override
            public void onSelect(VOInterface item) {
                dismiss();
                onItemListenner.onItemSelected(item);
            }

            @Override
            public void onMarkOn(VOInterface item) {
                mToolbar.configureEditMode();
            }

            @Override
            public void onMarkOff() {
                mToolbar.configureCreateMode();
            }

        });

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showItemManager(@NonNull final VOInterface vo) {

        TextComponentDialog.newInstance(R.string.transaction_tag, vo.getName(), new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {
                vo.setName(value);
                mAdapter.addItem(vo);
                mAdapter.markItemOff();
                mToolbar.configureCreateMode();
                onItemListenner.onItemAdded(vo);
            }
        }).show(getChildFragmentManager(), TextComponentDialog.TAG);
    }

    public ListComponentDialog addOnItemListenner(OnItemListenner onAddItemListenner) {
        this.onItemListenner = onAddItemListenner;
        return this;
    }

    /**
     * Dialog listener
     */

    @Override
    public void onAddClick() {
        showItemManager(onItemListenner.onIntanceRequested());
    }

    @Override
    public void onEditClick() {
        showItemManager(mAdapter.getMarkedItem());
    }

    @Override
    public void onDeleteClick() {
        VOInterface markedItem = mAdapter.getMarkedItem();
        mAdapter.removeItem(markedItem);
        mAdapter.markItemOff();
        mToolbar.configureCreateMode();
        onItemListenner.onItemDeleted(markedItem);
    }

    public void addList(List<VOInterface> list) {
        mAdapter.addList(list);
    }

    /**
     *
     */
    public interface OnItemListenner {

        VOInterface onIntanceRequested();

        void onItemAdded(VOInterface item);

        void onItemDeleted(VOInterface item);

        void onItemSelected(VOInterface item);
    }
}
