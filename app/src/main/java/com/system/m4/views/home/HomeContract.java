package com.system.m4.views.home;

import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomeContract {

    /**
     *
     */
    interface View {

        void setPresenter(Presenter presenter);

        void setListTransactions(List<TransactionVO> list);

        void showTransactionDialog(TagVO vo);

        void showTransactionDialog(TransactionVO vo);

        void showError(String message);

        void refreshOff();

        void showSuccessMessage(int template, int param);

        void requestTransactionManagerDialog();
    }

    /**
     *
     */
    interface Presenter {

        void requestListTransaction();

        void requestTransactionManager();

        void requestTransactionDialog(TagVO vo);

        void selectItem(TransactionVO vo);

        void markItemOn(TransactionVO item);

        void markItemOff();
    }
}
