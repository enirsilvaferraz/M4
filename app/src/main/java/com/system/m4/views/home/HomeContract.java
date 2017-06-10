package com.system.m4.views.home;

import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOItemListInterface;

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

        void setListTransactions(List<VOItemListInterface> listVo);

        void showTransactionDialog(TagVO vo);

        void showTransactionDialog(Transaction vo);

        void showError(String message);

        void showSuccessMessage(int template, int param);

        void requestTransactionManagerDialog();

        void requestDelete();

        void configureReadMode();

        void configureEditMode(boolean canPin);

        void markItemOff();
    }

    /**
     *
     */
    interface Presenter {

        void requestListTransaction();

        void requestTransactionManager();

        void requestTransactionDialog(TagVO vo);

        void selectItem(Transaction vo);

        void markItemOn(Transaction item);

        void markItemOff();

        void requestDelete();

        void requestCopy();

        void delete();

        void pinTransaction(boolean pin);
    }
}
