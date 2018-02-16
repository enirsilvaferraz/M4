package com.system.m4.views.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.system.m4.views.vos.RedirectButtomVO;
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

        void openDeleteDialog(TransactionVO item);

        void requestShowListTransaction(TagSummaryVO item);

        Context getContext();

        void openDetail(RedirectButtomVO vo);

        void showPoupu(android.view.View viewClicked, TransactionVO vo);
    }

    /**
     *
     */
    public interface Presenter {

        void init(int relativePosition, int homeVisibility);

        void requestListTransaction();

        void selectItem(TransactionVO vo);

        void requestCopy(@NonNull TransactionVO item);

        void requestDelete(@NonNull TransactionVO item);

        void onConfirmDeleteClicked(TransactionVO item);

        void requestShowListTransaction(@NonNull TagSummaryVO item);

        void onClickVO(VOItemListInterface vo);

        boolean onLongClickVO(VOItemListInterface vo, android.view.View view);
    }
}
