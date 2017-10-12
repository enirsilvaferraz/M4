package com.system.m4.views.home;

import com.system.m4.views.vos.TagSummaryVO;
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

        void showTransactionDialog(Transaction vo);

        void showError(String message);

        void showSuccessMessage(int template, int param);

        void requestDelete(Transaction item);

        void requestShowListTransaction(TagSummaryVO item);
    }

    /**
     *
     */
    interface Presenter {

        void init(int relativePosition);

        void requestListTransaction();

        void selectItem(Transaction vo);

        void requestCopy(Transaction item);

        void requestDelete(Transaction item);

        void delete(Transaction item);

        void pinTransaction(Transaction item);

        void unpinTransaction(Transaction item);

        void requestShowListTransaction(TagSummaryVO item);
    }
}
