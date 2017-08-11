package com.system.m4.views.home;

import android.support.annotation.NonNull;

import com.system.m4.businness.HomeBusiness;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.ChartItemVO;
import com.system.m4.views.vos.ChartVO;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SpaceVO;
import com.system.m4.views.vos.SubTitleVO;
import com.system.m4.views.vos.SummaryVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TitleVO;
import com.system.m4.views.vos.Transaction;
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

    private Calendar instance;

    HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void init(int relativePosition) {

        instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, relativePosition);
    }

    @Override
    public void requestListTransaction() {

        HomeBusiness.findTransactions(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), new BusinnessListener.OnSingleResultListener<ListTransactionVO>() {

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

            Collections.sort(listGroup, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    if (o1.getPurchaseDate() != null && o2.getPurchaseDate() != null) {
                        return o1.getPurchaseDate().compareTo(o2.getPurchaseDate()) * -1;
                    } else {
                        return o1.compareTo(o2);
                    }
                }
            });

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

                transaction.setTag(new TagVO());
                transaction.getTag().setName(key.getName());

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

            listVO.add(getChart(listTransaction));
            listVO.add(new SpaceVO());

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

            listVO.add(0, new TitleVO(JavaUtils.DateUtil.format(instance.getTime(), JavaUtils.DateUtil.MMMM_DE_YYYY)));
            listVO.add(1, new SpaceVO());
            mView.setListTransactions(listVO);
        }
    }

    @NonNull
    private ChartVO getChart(List<Transaction> transactions) {

        List<ChartItemVO> chartItems = new ArrayList<>();
        for (Transaction transaction : transactions) {

            if (transaction.getKey() == null) {
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

        listVO.add(new SubTitleVO("Summary"));
        listVO.add(new SummaryVO("Actual spending", actual));
        listVO.add(new SummaryVO("Confirmed spending", actual + future));
        listVO.add(new SummaryVO("Expected spending", expected));
        listVO.add(new SummaryVO("Total spending", actual + future + expected));
        listVO.add(new SpaceVO());
    }

    @Override
    public void selectItem(Transaction vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void requestDelete(Transaction item) {
        mView.requestDelete(item);
    }

    @Override
    public void requestCopy(Transaction item) {
        item.setKey(null);
        mView.showTransactionDialog(item);
    }

    @Override
    public void delete(Transaction item) {
        TransactionBusinness.delete(item, new BusinnessListener.OnPersistListener<DTOAbs>() {
            @Override
            public void onSuccess(DTOAbs dto) {
                requestListTransaction();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void pinTransaction(Transaction item) {
        TransactionBusinness.pin(item, new BusinnessListener.OnPersistListener<Transaction>() {

            @Override
            public void onSuccess(Transaction dto) {
                requestListTransaction();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void unpinTransaction(Transaction item) {
        TransactionBusinness.unpin(item, new BusinnessListener.OnPersistListener<Transaction>() {

            @Override
            public void onSuccess(Transaction dto) {
                requestListTransaction();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }
}
