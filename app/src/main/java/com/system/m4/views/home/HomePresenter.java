package com.system.m4.views.home;

import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.Collections;

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

        TransactionBusinness.findByFilter(new BusinnessListener.OnSingleResultListener<ListTransactionVO>() {

            @Override
            public void onSuccess(ListTransactionVO vo) {

                Collections.sort(vo.getCurrentList());
                Collections.sort(vo.getFutureList());

                mView.setListTransactions(vo);
            }

            @Override
            public void onError(Exception e) {
                mView.setListTransactions(new ListTransactionVO());
                mView.showError(e.getMessage());
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
        mView.configureEditMode(!vo.isPinned());
    }

    @Override
    public void markItemOff() {
        this.mSelectedItem = null;
        mView.configureReadMode();
        mView.markItemOff();
    }

    @Override
    public void requestDelete() {
        mView.requestDelete();
    }

    @Override
    public void requestCopy() {
        mSelectedItem.setKey(null);
        mView.showTransactionDialog(mSelectedItem);
        markItemOff();
    }

    @Override
    public void delete() {
        TransactionBusinness.delete(ConverterUtils.fromTransaction(mSelectedItem), new BusinnessListener.OnPersistListener() {
            @Override
            public void onSuccess(DTOAbs dto) {
                markItemOff();
                requestListTransaction();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void pinTransaction(boolean pin) {

        mSelectedItem.setPinned(pin);
        TransactionBusinness.save(mSelectedItem, new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess(DTOAbs dto) {
                markItemOff();
                requestListTransaction();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }
}
