package com.system.m4.views.home;

import android.support.annotation.NonNull;
import android.view.View;

import com.system.m4.kotlin.home.HomeBusiness;
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener;
import com.system.m4.kotlin.infrastructure.listeners.SingleResultListener;
import com.system.m4.kotlin.transaction.TransactionBusiness;
import com.system.m4.kotlin.transaction.TransactionModel;
import com.system.m4.views.vos.AmountVO;
import com.system.m4.views.vos.HomeVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SpaceVO;
import com.system.m4.views.vos.SubTitleVO;
import com.system.m4.views.vos.SummaryVO;
import com.system.m4.views.vos.TagSummaryVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    private Calendar date;

    public HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void init(int relativePosition) {
        date = Calendar.getInstance();
        date.add(Calendar.MONTH, relativePosition);
        requestListTransaction();
    }

    @Override
    public void requestListTransaction() {

        new HomeBusiness().findHomeList(date.get(Calendar.YEAR), date.get(Calendar.MONTH), new SingleResultListener<HomeVO>() {

            @Override
            public void onSuccess(HomeVO item) {
                configureHeaderList(item);
            }

            @Override
            public void onError(@NonNull String e) {
                mView.setListTransactions(new ArrayList<VOItemListInterface>());
                mView.showError(e);
            }
        });
    }

    private void configureHeaderList(HomeVO item) {

        List<VOItemListInterface> listVO = new ArrayList<>();

        listVO.add(new SubTitleVO("Resumo"));
        listVO.add(new SummaryVO("Total Gasto", item.getAmount()));
        listVO.add(new SummaryVO("Total Retornado", item.getRefound()));
        listVO.add(new SummaryVO("Total Real", item.getAmount() - item.getRefound()));
        listVO.add(new SpaceVO());

        if (!item.getPendingTransaction().isEmpty()) {
            listVO.add(new SubTitleVO("Transações Pendentes"));
            listVO.addAll(item.getPendingTransaction());
            listVO.add(new SpaceVO());
        }

        if (!item.getTransactions1Q().getTransactions().isEmpty()) {
            listVO.add(new SubTitleVO("Transações da 1a quinzena"));
            listVO.addAll(item.getTransactions1Q().getTransactions());
            listVO.add(new AmountVO(item.getTransactions1Q().getAmount()));
            listVO.add(new SpaceVO());
        }

        if (!item.getTransactions2Q().getTransactions().isEmpty()) {
            listVO.add(new SubTitleVO("Transações da 2a quinzena"));
            listVO.addAll(item.getTransactions2Q().getTransactions());
            listVO.add(new AmountVO(item.getTransactions2Q().getAmount()));
            listVO.add(new SpaceVO());
        }

        for (PaymentTypeVO key : item.getGroups().keySet()) {
            listVO.add(new SubTitleVO(key.getName()));
            listVO.addAll(item.getGroups().get(key).getTransactions());
            listVO.add(new AmountVO(item.getGroups().get(key).getAmount()));
            listVO.add(new SpaceVO());
        }

        if (!item.getTagSummary().isEmpty()) {
            listVO.add(new SubTitleVO("Resumo das Tags"));
            listVO.addAll(item.getTagSummary());
            listVO.add(new SpaceVO());
        }

        mView.setListTransactions(listVO);
    }

    @Override
    public void selectItem(TransactionVO vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void requestDelete(@NonNull TransactionVO item) {
        mView.openDeleteDialog(item);
    }

    @Override
    public void requestCopy(@NonNull TransactionVO item) {
        item.setKey(null);
        mView.showTransactionDialog(item);
    }

    @Override
    public void onConfirmDeleteClicked(TransactionVO item) {
        TransactionBusiness.Companion.delete(item, new PersistenceListener<TransactionModel>() {
            @Override
            public void onSuccess(TransactionModel transactionVO) {
                requestListTransaction();
            }

            @Override
            public void onError(@NonNull String e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void requestShowListTransaction(@NonNull TagSummaryVO item) {
        mView.requestShowListTransaction(item);
    }

    @Override
    public void onClickVO(VOItemListInterface vo) {

        if (vo instanceof TransactionVO) {
            selectItem(((TransactionVO) vo));
        } else if (vo instanceof TagSummaryVO) {
            requestShowListTransaction(((TagSummaryVO) vo));
        }
    }

    @Override
    public boolean onLongClickVO(VOItemListInterface vo, View view) {

        if (vo instanceof TransactionVO) {
            mView.showPoupu(view, (TransactionVO) vo);
            return true;
        }

        return false;
    }
}
