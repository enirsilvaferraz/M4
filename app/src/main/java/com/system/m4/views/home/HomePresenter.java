package com.system.m4.views.home;

import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void requestListTransaction() {

        FilterTransactionVO vo = new FilterTransactionVO();
        vo.setPaymentDateStart(JavaUtils.DateUtil.getActualMinimum(new Date()));
        vo.setPaymentDateEnd(JavaUtils.DateUtil.getActualMaximum(new Date()));

        TransactionBusinness.findByFilter(vo, new BusinnessListener.OnMultiResultListenner<TransactionVO>() {

            @Override
            public void onSuccess(List<TransactionVO> list) {

                Collections.sort(list, new Comparator<TransactionVO>() {
                    @Override
                    public int compare(TransactionVO vo0, TransactionVO vo1) {
                        return vo0.getPaymentDate().compareTo(vo1.getPaymentDate());
                    }
                });

                mView.setListTransactions(list);
                mView.refreshOff();
            }

            @Override
            public void onError(Exception e) {
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
    public void requestTransactionDialog(TransactionVO vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void requestTransactionDialog(TagVO vo) {
        mView.showTransactionDialog(vo);
    }
}
