package com.system.m4.views.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.ListTagPresenter;
import com.system.m4.views.transaction.TransactionManagerDialog;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOInterface;
import com.system.m4.views.vos.VOItemListInterface;

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

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setAdapter(new TransactionAdapter(presenter));

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
    public void setListTransactions(List<VOItemListInterface> listVo) {
        TransactionAdapter adapter = (TransactionAdapter) mRecyclerview.getAdapter();
        adapter.clearList();
        adapter.addCurrentList(listVo);
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
    public void requestDelete(final Transaction item) {
        JavaUtils.AndroidUtil.showAlertDialog(getContext(), R.string.system_message_request_delete,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.delete(item);
                    }
                });
    }

    @Override
    public void showTransactionDialog(TagVO vo) {
        showTransactionDialog(new Transaction(vo));
    }

    @Override
    public void showTransactionDialog(Transaction vo) {
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
    public void showSuccessMessage(int template, int param) {
        String message = getString(template, getString(param));
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
