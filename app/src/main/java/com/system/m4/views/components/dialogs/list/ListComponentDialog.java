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

public class ListComponentDialog extends BaseDialogFragment implements ListComponentContract.View {

    @BindView(R.id.dialog_list_recycler)
    RecyclerView recyclerview;

    Unbinder unbinder;

    private ListComponentAdapter mAdapter;
    private ListComponentContract.Presenter presenter;
    private DialogListener listener;

    public static ListComponentDialog newInstance(@StringRes int title, DialogListener listener) {

        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_BUNDLE, title);

        ListComponentDialog dialog = new ListComponentDialog();
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

        hideFooter();
        setTitle(getArguments().getInt(TITLE_BUNDLE));
        mToolbar.configureCreateMode();
        mToolbar.setListener(this);

        List<VOInterface> list = new ArrayList<>();
        mAdapter = new ListComponentAdapter(list, presenter);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);

        presenter.init();
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
    public void onTitleClick() {
        // DO NOTHING
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

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public void setPresenter(ListComponentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDoneClick() {
    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    @Override
    public void configureNoActionMode() {
        mToolbar.configureNoActionMode();
        mAdapter.configureNoActionMode();
    }
}
