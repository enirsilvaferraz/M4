package com.system.m4.views.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.ListTagPresenter;
import com.system.m4.views.transaction.TransactionManagerDialog;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements HomeContract.View {

    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.home_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    Unbinder unbinder;

    private HomeContract.Presenter presenter;

    public HomeFragment() {
        // Nothing to do
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestListTransaction();
            }
        });

        mSwipeRefresh.setColorSchemeResources(R.color.accent,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        this.presenter.requestListTransaction();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListTransactions(List<TransactionVO> list) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setAdapter( new TransactionAdapter(presenter, list));
        mRecyclerview.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void requestTransactionManagerDialog() {

        ListComponentDialog listComponentDialog = ListComponentDialog.newInstance(R.string.transaction_tag, new BaseDialogFragment.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.requestTransactionDialog((TagVO) vo);
            }
        });

        listComponentDialog.setPresenter(new ListTagPresenter(listComponentDialog));
        listComponentDialog.show(getChildFragmentManager(), ListComponentDialog.class.getSimpleName());
    }

    @Override
    public void showTransactionDialog(TagVO vo) {
        showTransactionDialog(new TransactionVO(vo));
    }

    @Override
    public void showTransactionDialog(TransactionVO vo) {
        TransactionManagerDialog dialogFragment = TransactionManagerDialog.newInstance(vo);
        dialogFragment.setDialogListener(new BaseDialogFragment.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.requestListTransaction();
            }
        });
        dialogFragment.show(getChildFragmentManager(), TransactionManagerDialog.class.getSimpleName());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshOff() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showSuccessMessage(int template, int param) {
        String message = getString(template, getString(param));
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
