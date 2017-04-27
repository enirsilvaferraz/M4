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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerview;

    Unbinder unbinder;

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

        ArrayList<TransactionAdapter.ItemVO> list = new ArrayList<>();
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));
        list.add(new TransactionAdapter.ItemVO("", "", "", "", "", ""));

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setAdapter(new TransactionAdapter(list, new TransactionAdapter.OnItemSelectedListener() {
            @Override
            public void onSelect(TransactionAdapter.ItemVO item) {

            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
