package com.system.m4.views.home;

import android.support.annotation.NonNull;

import com.system.m4.kotlin.home.HomeBusiness;
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener;
import com.system.m4.kotlin.infrastructure.listeners.SingleResultListener;
import com.system.m4.kotlin.transaction.TransactionBusiness;
import com.system.m4.kotlin.transaction.TransactionModel;
import com.system.m4.views.vos.ChartItemVO;
import com.system.m4.views.vos.ChartVO;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SpaceVO;
import com.system.m4.views.vos.SubTitleVO;
import com.system.m4.views.vos.SummaryVO;
import com.system.m4.views.vos.TagSummaryVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    private Calendar date;

    HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void init(int relativePosition) {

        date = Calendar.getInstance();
        date.add(Calendar.MONTH, relativePosition);
    }

    @Override
    public void requestListTransaction() {

        new HomeBusiness().findHomeList(date.get(Calendar.YEAR), date.get(Calendar.MONTH), new SingleResultListener<ListTransactionVO>() {

            @Override
            public void onSuccess(ListTransactionVO item) {
                configureHeaderList(item);
            }

            @Override
            public void onError(String e) {
                mView.setListTransactions(new ArrayList<VOItemListInterface>());
                mView.showError(e);
            }
        });
    }

    private void configureHeaderList(ListTransactionVO item) {

        if (item != null) {

            Collections.sort(item.getTransactions());

            List<TransactionVO> listTransaction = new ArrayList<>();
            List<TransactionVO> listGroup = new ArrayList<>();

            for (TransactionVO transaction : item.getTransactions()) {
                if (!item.getGroup().getPaymentTypeList().contains(transaction.getPaymentType())) {
                    listTransaction.add(transaction);
                } else {
                    listGroup.add(transaction);
                }
            }

            Collections.sort(listGroup, new Comparator<TransactionVO>() {
                @Override
                public int compare(TransactionVO o1, TransactionVO o2) {
                    if (o1.getPurchaseDate() != null && o2.getPurchaseDate() != null) {
                        return o1.getPurchaseDate().compareTo(o2.getPurchaseDate()) * -1;
                    } else {
                        return o1.compareTo(o2);
                    }
                }
            });

            Map<PaymentTypeVO, List<TransactionVO>> map = new HashMap<>();

            for (TransactionVO itemGroup : listGroup) {

                if (!map.containsKey(itemGroup.getPaymentType())) {
                    map.put(itemGroup.getPaymentType(), new ArrayList<TransactionVO>());
                }

                for (PaymentTypeVO key : map.keySet()) {
                    if (key.equals(itemGroup.getPaymentType())) {
                        map.get(key).add(itemGroup);
                    }
                }
            }

            for (PaymentTypeVO key : map.keySet()) {

                TransactionVO transaction = new TransactionVO();
                transaction.setPaymentType(key);

                transaction.setTag(new TagVO());
                transaction.getTag().setName(key.getName());
                transaction.getTag().setParentName("Grupo de transações");

                for (TransactionVO itemList : map.get(key)) {
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

//            ChartVO chartVO = getChart(item.getTransactions());
//            if (!chartVO.getItems().isEmpty()) {
//                listVO.add(chartVO);
//                listVO.add(new SpaceVO());
//            }

            if (!item.getPendingTransaction().isEmpty()) {
                listVO.add(new SubTitleVO("Pending Transactions"));
                listVO.addAll(item.getPendingTransaction());
                listVO.add(new SpaceVO());
            }

            if (!item.getTagSummary().isEmpty()) {
                listVO.add(new SubTitleVO("Tag Summary"));
                listVO.addAll(item.getTagSummary());
                listVO.add(new SpaceVO());
            }

            if (!listTransaction.isEmpty()) {
                listVO.add(new SubTitleVO("Transactions"));
                listVO.addAll(listTransaction);
                listVO.add(new SpaceVO());
            }

            for (PaymentTypeVO key : map.keySet()) {
                listVO.add(new SubTitleVO(key.getName()));
                listVO.addAll(map.get(key));
                listVO.add(new SpaceVO());
            }

            mView.setListTransactions(listVO);
        }
    }

    @NonNull
    private ChartVO getChart(List<TransactionVO> transactions) {

        List<ChartItemVO> chartItems = new ArrayList<>();
        for (TransactionVO transaction : transactions) {

            if (transaction.getKey() == null || !transaction.isApproved() || transaction.getPaymentDate().compareTo(Calendar.getInstance().getTime()) > 0) {
                continue;
            }

            ChartItemVO chartItem = new ChartItemVO(transaction.getTag().getName(), transaction.getPrice().floatValue());

            if (chartItems.contains(chartItem)) {
                ChartItemVO item = chartItems.get(chartItems.indexOf(chartItem));
                item.setValue(item.getValue() + transaction.getPrice().floatValue());
            } else {
                chartItems.add(chartItem);
            }
        }

        Collections.sort(chartItems, new Comparator<ChartItemVO>() {
            @Override
            public int compare(ChartItemVO o1, ChartItemVO o2) {
                return Float.valueOf(o1.getValue()).compareTo(o2.getValue()) *-1;
            }
        });

        ChartVO chart = new ChartVO();
        chart.setItems(chartItems.subList(0, Math.min(10, chartItems.size())));

        return chart;
    }

    private void configSummary(List<VOItemListInterface> listVO, List<TransactionVO> listTransaction) {

        Double actual = 0D;
        Double expected = 0D;
        Double future = 0D;
        Double fixed = 0D;

        for (TransactionVO transaction : listTransaction) {

            if (transaction.isFixed()) {
                fixed += transaction.getPrice();
            }

            if (!transaction.isApproved()) {
                expected += transaction.getPrice();
            } else if (transaction.getPaymentDate().compareTo(Calendar.getInstance().getTime()) <= 0) {
                actual += transaction.getPrice();
            } else {
                future += transaction.getPrice();
            }
        }

        listVO.add(new SubTitleVO("Summary"));
        listVO.add(new SummaryVO("Actual spending", actual));
        listVO.add(new SummaryVO("Confirmed spending", actual + future));
        listVO.add(new SummaryVO("Expected spending", expected));
        listVO.add(new SummaryVO("Essential spending", fixed));
        listVO.add(new SummaryVO("Total spending", actual + future + expected));
        listVO.add(new SpaceVO());
    }

    @Override
    public void selectItem(TransactionVO vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void requestDelete(TransactionVO item) {
        mView.requestDelete(item);
    }

    @Override
    public void requestCopy(TransactionVO item) {
        item.setKey(null);
        mView.showTransactionDialog(item);
    }

    @Override
    public void delete(TransactionVO item) {
        TransactionBusiness.Companion.delete(mView.getContext(), item, new PersistenceListener<TransactionModel>() {
            @Override
            public void onSuccess(TransactionModel transactionVO) {
                requestListTransaction();
            }

            @Override
            public void onError(String e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void requestShowListTransaction(TagSummaryVO item) {
        mView.requestShowListTransaction(item);
    }

    @Override
    public void requestPin(TransactionVO item) {
        TransactionBusiness.Companion.pin(mView.getContext(), item, new PersistenceListener<TransactionVO>() {
            @Override
            public void onSuccess(TransactionVO vo) {
                requestListTransaction();
            }

            @Override
            public void onError(String e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void requestUnpin(TransactionVO item) {
        TransactionBusiness.Companion.unpin(mView.getContext(), item, new PersistenceListener<TransactionVO>() {
            @Override
            public void onSuccess(TransactionVO vo) {
                requestListTransaction();
            }

            @Override
            public void onError(String e) {
                mView.showError(e);
            }
        });
    }
}
