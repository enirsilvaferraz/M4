package com.system.m4.views.home;

import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SpaceVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TitleVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

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

        TransactionBusinness.findByFilter(new BusinnessListener.OnSingleResultListener<ListTransactionVO>() {

            @Override
            public void onSuccess(ListTransactionVO item) {
                configureHeaderList(item);
            }

            @Override
            public void onError(Exception e) {
                mView.setListTransactions(new ArrayList<VOItemListInterface>());
                mView.showError(e.getMessage());
            }
        });
    }

    private void configureHeaderList(ListTransactionVO item) {

        Collections.sort(item.getTransactions());
        List<VOItemListInterface> listInterface = new ArrayList<>();

        List<VOItemListInterface> listParcial = new ArrayList<>();
        listParcial.add(new TitleVO("Transactions"));
        for (TransactionVO transactionVO : item.getTransactions()) {
            if (!item.getGroup().getPaymentTypeList().contains(transactionVO.getPaymentType())) {
                listParcial.add(transactionVO);
            }
        }
        listParcial.add(new SpaceVO());

        if (listParcial.size() > 2) {
            listInterface.addAll(listParcial);
        }

        for (PaymentTypeVO typeVO : item.getGroup().getPaymentTypeList()) {

            listParcial = new ArrayList<>();
            listParcial.add(new TitleVO(typeVO.getName()));
            for (TransactionVO transactionVO : item.getTransactions()) {
                if (transactionVO.getPaymentType().equals(typeVO)) {
                    listParcial.add(transactionVO);
                }
            }
            listParcial.add(new SpaceVO());

            if (listParcial.size() > 2) {
                listInterface.addAll(listParcial);
            }
        }

        mView.setListTransactions(listInterface);
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
