package com.system.m4.views.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.system.m4.kotlin.recycler.GenericPresenter;
import com.system.m4.views.vos.TagSummaryVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOItemListInterface;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public class HomeContract {

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
    interface Presenter extends GenericPresenter {

        void init(int relativePosition, int homeVisibility);

        void requestListTransaction();

        void selectItem(TransactionVO vo);

        void requestCopy(@NonNull TransactionVO item);

        void requestDelete(@NonNull TransactionVO item);

        void delete(TransactionVO item);

        void requestShowListTransaction(@NonNull TagSummaryVO item);

        void requestPin(@NonNull TransactionVO item);

        void requestUnpin(@NonNull TransactionVO item);
    }
}
