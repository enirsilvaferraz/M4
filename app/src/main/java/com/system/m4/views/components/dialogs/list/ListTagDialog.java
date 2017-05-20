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
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.TextComponentDialog;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by eferraz on 18/05/17.
 * For M4
 */

public class ListTagDialog extends BaseDialogFragment implements ListComponentContract.View {

    @BindView(R.id.dialog_list_recycler)
    RecyclerView recyclerview;

    Unbinder unbinder;

    private ListTagAdapter mAdapter;
    private ListComponentContract.Presenter presenter;
    private ListComponentContract.DialogListener listener;

    public static ListTagDialog newInstance(@StringRes int title, ListComponentContract.DialogListener listener) {

        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_BUNDLE, title);

        ListTagDialog dialog = new ListTagDialog();
        dialog.setArguments(bundle);
        dialog.setListener(listener);
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

        presenter = new ListTagPresenter(this);

        hideDoneBtn();
        setTitle(getArguments().getInt(TITLE_BUNDLE));
        mToolbar.configureCreateMode();
        mToolbar.setOnClickListener(this);

        List<VOInterface> list = new ArrayList<>();
        mAdapter = new ListTagAdapter(list, presenter);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);

        presenter.requestList();
    }

    @Override
    public void renderList(List<VOInterface> list) {
        mAdapter.addList(list);
    }

    @Override
    public void showNewItemDialog(@NonNull final VOInterface vo) {
        TextComponentDialog.newInstance(R.string.transaction_tag, vo.getName(), new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {
                presenter.save(value, vo);
            }
        }).show(getChildFragmentManager(), TextComponentDialog.TAG);
    }

    @Override
    public void addItem(@NonNull VOInterface vo) {
        mAdapter.addItem(vo);
    }

    @Override
    public void changeItem(@NonNull VOInterface vo) {
        mAdapter.changeItem(vo);
    }

    @Override
    public void deleteItem(@NonNull VOInterface vo) {
        mAdapter.removeItem(vo);
    }

    @Override
    public void selectItem(@NonNull VOInterface vo) {
        listener.onFinish(vo);
    }

    @Override
    public void markItemOff() {
        mAdapter.markItemOff();
    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void configureCreateMode() {
        mToolbar.configureCreateMode();
    }

    @Override
    public void configureEditMode() {
        mToolbar.configureEditMode();
    }

    @Override
    public void onAddClick() {
        presenter.requestAdd();
    }

    @Override
    public void onEditClick() {
        presenter.requestEdit();
    }

    @Override
    public void onDeleteClick() {
        presenter.requestDelete();
    }

    public ListComponentContract.DialogListener getListener() {
        return listener;
    }

    public void setListener(ListComponentContract.DialogListener listener) {
        this.listener = listener;
    }
}
