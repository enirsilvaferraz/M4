package com.system.m4.views.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.kotlin.home.HomeAdapter;
import com.system.m4.kotlin.transaction.TransactionListDialog;
import com.system.m4.views.vos.TagSummaryVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements HomeContract.View {

    public static final String RELATIVE_POSITION = "RELATIVE_POSITION";

    private RecyclerView mRecyclerview;

    private HomeContract.Presenter presenter;

    public HomeFragment() {
        // Nothing to do
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerview = view.findViewById(R.id.home_recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setAdapter(new HomeAdapter(new HomeListener()));

        this.presenter.init(getArguments().getInt(RELATIVE_POSITION));
    }

    public void requestListTransaction() {
        presenter.requestListTransaction();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListTransactions(List<VOItemListInterface> listVo) {
        HomeAdapter adapter = (HomeAdapter) mRecyclerview.getAdapter();
        adapter.clearList();
        adapter.addCurrentList(listVo);
    }

    @Override
    public void showTransactionDialog(TransactionVO vo) {
        ((MainActivity) getActivity()).showTransactionDialog(vo);
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

    @Override
    public void openDeleteDialog(final TransactionVO item) {
        JavaUtils.AndroidUtil.showAlertDialog(getContext(), R.string.system_message_request_delete,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onConfirmDeleteClicked(item);
                    }
                });
    }

    @Override
    public void requestShowListTransaction(TagSummaryVO item) {
        TransactionListDialog dialogFragment = TransactionListDialog.Companion.instance(new ArrayList<>(item.getTransactions()));
        dialogFragment.show(getFragmentManager(), TransactionListDialog.class.getSimpleName());
    }

    @Override
    public void showPoupu(View viewClicked, final TransactionVO vo) {

        PopupMenu popupMenu = new PopupMenu(getContext(), viewClicked);
        popupMenu.inflate(R.menu.menu_transaction);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_copy:
                        presenter.requestCopy(vo);
                        return true;

                    case R.id.action_delete:
                        presenter.requestDelete(vo);
                        return true;
                }

                return false;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), (MenuBuilder) popupMenu.getMenu(), viewClicked);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    private class HomeListener implements HomeAdapter.Listener {

        @Override
        public void onClickVO(@NotNull VOItemListInterface vo, @NotNull View view) {
            presenter.onClickVO(vo);
        }

        @Override
        public boolean onLongClickVO(@NotNull VOItemListInterface vo, @NotNull View view) {
            return presenter.onLongClickVO(vo, view);
        }
    }
}
