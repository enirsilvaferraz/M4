package com.system.m4.views.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.m4.R;
import com.system.m4.views.transaction.ItemVO;
import com.system.m4.views.transaction.TransactionManagerDialog;

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

    Unbinder unbinder;

    private HomeContract.Presenter presenter;

    public HomeFragment() {
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

        presenter = new HomePresenter(this);
        presenter.requestListTransaction();

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setListTransactions(List<ItemVO> list) {

        TransactionAdapter adapter = new TransactionAdapter(list, new TransactionAdapter.OnItemSelectedListener() {
            @Override
            public void onSelect(ItemVO item) {
                TransactionManagerDialog.newInstance(item).show(getChildFragmentManager(), TransactionManagerDialog.TAG);
            }
        });

        mRecyclerview.setAdapter(adapter);
        mRecyclerview.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
