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
import com.system.m4.views.components.dialogs.list.ListComponentContract;
import com.system.m4.views.components.dialogs.list.ListTagDialog;
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
    private ListTagDialog listComponentDialog;

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

        refreshOff();

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
    public void showTransactionManagerDialog() {

//        listComponentDialog = ListComponentDialog.newInstance(R.string.transaction_tag);
//        listComponentDialog.addOnItemListenner(new ListComponentDialog.OnItemListenner() {
//
//            @Override
//            public VOInterface onIntanceRequested() {
//                return new TagVO();
//            }
//
//            @Override
//            public void onItemAdded(VOInterface item) {
//                presenter.saveTag(item);
//            }
//
//            @Override
//            public void onItemDeleted(VOInterface item) {
//                presenter.deleteTag(item);
//            }
//
//            @Override
//            public void onItemSelected(VOInterface item) {
//                TransactionManagerDialog dialogFragment = TransactionManagerDialog.newInstance(new TransactionVO(((TagVO) item)));
//                dialogFragment.setDialogListener(new TransactionManagerDialog.DialogListener() {
//                    @Override
//                    public void onDismiss() {
//                        presenter.requestListTransaction();
//                    }
//                });
//                dialogFragment.show(getChildFragmentManager(), TransactionManagerDialog.TAG);
//            }
//
//        }).show(getChildFragmentManager());

        listComponentDialog = ListTagDialog.newInstance(R.string.transaction_tag, new ListComponentContract.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {

                TransactionManagerDialog dialogFragment = TransactionManagerDialog.newInstance(new TransactionVO(((TagVO) vo)));
                dialogFragment.setDialogListener(new TransactionManagerDialog.DialogListener() {
                    @Override
                    public void onDismiss() {
                        presenter.requestListTransaction();
                    }
                });

                dialogFragment.show(getChildFragmentManager(), TransactionManagerDialog.class.getSimpleName());
            }
        });

        listComponentDialog.show(getChildFragmentManager(), ListTagDialog.class.getSimpleName());
    }

    @Override
    public void configureListTagsTransactionManager(List<TagVO> list) {
       // listComponentDialog.addList(new ArrayList<VOInterface>(list));
    }

    @Override
    public void showError(String message) {
        refreshOff();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void refreshOff() {
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
