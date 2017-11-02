package com.system.m4.views.home;

import android.content.Context;

import com.system.m4.views.vos.TagSummaryVO;
import com.system.m4.views.vos.TransactionVO;
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

        void showTransactionDialog(TransactionVO vo);

        void showError(String message);

        void showSuccessMessage(int template, int param);

        void requestDelete(TransactionVO item);

        void requestShowListTransaction(TagSummaryVO item);

        Context getContext();
    }

    /**
     *
     */
    interface Presenter {

        void init(int relativePosition);

        void requestListTransaction();

        void selectItem(TransactionVO vo);

        void requestCopy(TransactionVO item);

        void requestDelete(TransactionVO item);

        void delete(TransactionVO item);

        void requestShowListTransaction(TagSummaryVO item);

        void requestPin(TransactionVO item);

        void requestUnpin(TransactionVO item);
    }
}
