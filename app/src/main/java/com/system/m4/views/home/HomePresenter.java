package com.system.m4.views.home;

import com.system.m4.businness.HomeBusinness;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SpaceVO;
import com.system.m4.views.vos.SummaryVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TitleVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private Transaction mSelectedItem;

    HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void requestListTransaction() {

        HomeBusinness.findTransactions(new BusinnessListener.OnSingleResultListener<ListTransactionVO>() {

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

        if (item != null) {

            Collections.sort(item.getTransactions());

            List<Transaction> listTransaction = new ArrayList<>();
            List<Transaction> listGroup = new ArrayList<>();

            for (Transaction transaction : item.getTransactions()) {
                if (!item.getGroup().getPaymentTypeList().contains(transaction.getPaymentType())) {
                    listTransaction.add(transaction);
                } else {
                    listGroup.add(transaction);
                }
            }

            Map<PaymentTypeVO, List<Transaction>> map = new HashMap<>();

            for (Transaction itemGroup : listGroup) {

                if (!map.containsKey(itemGroup.getPaymentType())) {
                    map.put(itemGroup.getPaymentType(), new ArrayList<Transaction>());
                }

                for (PaymentTypeVO key : map.keySet()) {
                    if (key.equals(itemGroup.getPaymentType())) {
                        map.get(key).add(itemGroup);
                    }
                }
            }

            for (PaymentTypeVO key : map.keySet()) {

                Transaction transaction = new Transaction();
                transaction.setPaymentType(key);

                for (Transaction itemList : map.get(key)) {
                    Double price = transaction.getPrice() != null ? transaction.getPrice() : 0D;
                    transaction.setPrice(price + itemList.getPrice());
                    transaction.setPaymentDate(itemList.getPaymentDate());
                    transaction.setClickable(false);
                }

                listTransaction.add(transaction);
            }

            Collections.sort(listTransaction);

            List<VOItemListInterface> listVO = new ArrayList<>();

            configSummary(listVO, listTransaction);

            if (!listTransaction.isEmpty()) {
                listVO.add(new TitleVO("Transactions"));
                listVO.addAll(listTransaction);
                listVO.add(new SpaceVO());
            }

            for (PaymentTypeVO key : map.keySet()) {
                listVO.add(new TitleVO(key.getName()));
                listVO.addAll(map.get(key));
                listVO.add(new SpaceVO());
            }

            mView.setListTransactions(listVO);
        }
    }

    private void configSummary(List<VOItemListInterface> listVO, List<Transaction> listTransaction) {

        Double actual = 0D;
        Double expected = 0D;
        Double future = 0D;

        for (Transaction transaction : listTransaction) {

            if (!transaction.isApproved()) {
                expected += transaction.getPrice();
            } else if (transaction.getPaymentDate().compareTo(Calendar.getInstance().getTime()) <= 0) {
                actual += transaction.getPrice();
            } else {
                future += transaction.getPrice();
            }
        }

        listVO.add(new TitleVO("Summary"));
        listVO.add(new SummaryVO("Actual spending", actual));
        listVO.add(new SummaryVO("Confirmed spending", actual + future));
        listVO.add(new SummaryVO("Expected spending", expected));
        listVO.add(new SummaryVO("Total spending", actual + future + expected));
        listVO.add(new SpaceVO());
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
    public void selectItem(Transaction vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void markItemOn(Transaction vo) {
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
        TransactionBusinness.delete(mSelectedItem, new BusinnessListener.OnPersistListener<DTOAbs>() {
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

        if (pin) {

            TransactionBusinness.pin(mSelectedItem, new BusinnessListener.OnPersistListener<Transaction>() {

                @Override
                public void onSuccess(Transaction dto) {
                    markItemOff();
                    requestListTransaction();
                }

                @Override
                public void onError(Exception e) {
                    mView.showError(e.getMessage());
                }
            });

        } else {

            TransactionBusinness.unpin(mSelectedItem, new BusinnessListener.OnPersistListener<Transaction>() {

                @Override
                public void onSuccess(Transaction dto) {
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
}
