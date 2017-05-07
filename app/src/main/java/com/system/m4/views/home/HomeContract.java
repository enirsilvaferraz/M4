package com.system.m4.views.home;

import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOInterface;

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

        void setListTransactions(List<TransactionVO> list);

        void showTransactionManager(List<TagVO> list);

        void showError(String message);

        void showSuccessMessage(int template, int param);
    }

    /**
     *
     */
    interface Presenter {

        void requestListTransaction();

        void requestTransactionManager();

        void saveTag(VOInterface vo);

        void deleteTag(VOInterface vo);
    }
}
