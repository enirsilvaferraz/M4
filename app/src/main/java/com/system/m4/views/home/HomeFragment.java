package com.system.m4.views.home;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.system.m4.kotlin.home.HomeDetailActivity;
import com.system.m4.kotlin.transaction.TransactionListDialog;
import com.system.m4.views.vos.RedirectButtomVO;
import com.system.m4.views.vos.TagSummaryVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements HomeContract.View {

    public static final String RELATIVE_POSITION = "RELATIVE_POSITION";
    public static final String ITEM_VIEW = "ITEM_VIEW";

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
        mRecyclerview.setAdapter(new HomeAdapter(presenter));

        this.presenter.init(getArguments().getInt(RELATIVE_POSITION), getArguments().getInt(ITEM_VIEW));
        requestListTransaction();
    }

    public void requestListTransaction() {
        this.presenter.requestListTransaction();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setListTransactions(List<VOItemListInterface> listVo) {
        HomeAdapter adapter = (HomeAdapter) mRecyclerview.getAdapter();
        adapter.clearList();
        adapter.addCurrentList(listVo);
    }

    @Override
    public void requestDelete(final TransactionVO item) {
        JavaUtils.AndroidUtil.showAlertDialog(getContext(), R.string.system_message_request_delete,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.delete(item);
                    }
                });
    }

    @Override
    public void requestShowListTransaction(TagSummaryVO item) {
        TransactionListDialog dialogFragment = TransactionListDialog.Companion.instance(new ArrayList<>(item.getTransactions()));
        dialogFragment.show(getFragmentManager(), TransactionListDialog.class.getSimpleName());
    }

    @Override
    public void openDetail(RedirectButtomVO vo) {
        Intent intent = new Intent(getContext(), HomeDetailActivity.class);
        intent.putExtra(HomeFragment.ITEM_VIEW, vo.getHomeVisibility());
        intent.putExtra(HomeFragment.RELATIVE_POSITION, vo.getRelativePosition());
        startActivity(intent);
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

    public HomeContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPoupu(View viewClicked, final TransactionVO vo) {

        PopupMenu popupMenu = new PopupMenu(getContext(), viewClicked);
        popupMenu.inflate(R.menu.menu_transaction);

        popupMenu.getMenu().findItem(R.id.action_pin).setVisible(!vo.isFixed());
        popupMenu.getMenu().findItem(R.id.action_unpin).setVisible(vo.isFixed());

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

                    case R.id.action_pin:
                        presenter.requestPin(vo);
                        return true;

                    case R.id.action_unpin:
                        presenter.requestUnpin(vo);
                        return true;
                }

                return false;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), (MenuBuilder) popupMenu.getMenu(), viewClicked);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }
}
