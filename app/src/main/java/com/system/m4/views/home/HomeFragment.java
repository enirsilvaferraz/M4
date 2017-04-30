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
import com.system.m4.views.components.dialogs.list.ItemList;
import com.system.m4.views.components.dialogs.list.ListComponentAdapter;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.transaction.TransactionManagerDialog;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

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
        this.presenter.requestListTransaction();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListTransactions(List<TransactionVO> list) {

        TransactionAdapter adapter = new TransactionAdapter(list, new TransactionAdapter.OnItemSelectedListener() {
            @Override
            public void onSelect(TransactionVO item) {
                TransactionManagerDialog.newInstance(item).show(getChildFragmentManager(), TransactionManagerDialog.TAG);
            }
        });

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTransactionManager(List<TagVO> list) {

        ListComponentDialog.newInstance(R.string.transaction_tag, ItemList.asList(list)).addOnItemSelectedListener(new ListComponentAdapter.OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                TransactionVO vo = new TransactionVO(item.getName());
                TransactionManagerDialog.newInstance(vo).show(getChildFragmentManager(), TransactionManagerDialog.TAG);
            }
        }).addOnAddItemListenner(new ListComponentAdapter.OnAddItemListenner() {
            @Override
            public void onItemAdded(String content) {
                presenter.saveTag(content);
            }
        }).show(getChildFragmentManager());
    }

}
