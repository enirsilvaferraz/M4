package com.system.m4.views.home;

import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private TransactionVO mSelectedItem;

    HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void requestListTransaction() {

        TransactionBusinness.findByFilter(new BusinnessListener.OnMultiResultListenner<TransactionVO>() {

            @Override
            public void onSuccess(List<TransactionVO> list) {
                Collections.sort(list);
                mView.setListTransactions(list);
                mView.refreshOff();
            }

            @Override
            public void onError(Exception e) {
                mView.setListTransactions(new ArrayList<TransactionVO>());
                mView.showError(e.getMessage());
                mView.refreshOff();
            }
        });
    }

    @Override
    public void requestTransactionManager() {
        mView.requestTransactionManagerDialog(); // Colocar PLaceholder e remover if list == null do adapter
    }

    @Override
    public void requestTransactionDialog(TagVO vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void selectItem(TransactionVO vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void markItemOn(TransactionVO vo) {
        this.mSelectedItem = vo;
        //mView.configureEditMode();
    }

    @Override
    public void markItemOff() {
        this.mSelectedItem = null;
        //mView.configureCreateMode();
    }
}
