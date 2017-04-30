package com.system.m4.views.home;

import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

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
    }

    /**
     *
     */
    interface Presenter {

        void requestListTransaction();

        void requestTransactionManager();

        void saveTag(String tag);
    }
}
