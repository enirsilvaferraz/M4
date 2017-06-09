package com.system.m4.views.home;

import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.TagVO;
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

        TransactionBusinness.findByFilter(new BusinnessListener.OnMultiResultListenner<TransactionVO>() {

            @Override
            public void onSuccess(List<TransactionVO> list) {
                configureHeaderList(list);
            }

            @Override
            public void onError(Exception e) {
                mView.setListTransactions(new ArrayList<VOItemListInterface>());
                mView.showError(e.getMessage());
            }
        });
    }

    private void configureHeaderList(List<TransactionVO> list) {
        Collections.sort(list);

        List<VOItemListInterface> listInterface = new ArrayList<>();
        for (TransactionVO vo : list) {

//            TitleVO titleVO = new TitleVO(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(vo.getPaymentDate(), JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)));
//            if (!listInterface.contains(titleVO)) {
//                listInterface.add(titleVO);
//            }

            listInterface.add(vo);
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
