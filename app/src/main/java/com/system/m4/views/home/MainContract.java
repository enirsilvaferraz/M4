package com.system.m4.views.home;

import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

/**
 * Created by eferraz on 29/07/17.
 * For M4
 */

public interface MainContract {

    interface View{

        void requestTransactionManagerDialog();

        void showTransactionDialog(TagVO vo);

        void showTransactionDialog(Transaction vo);
    }

    interface Presenter {

        void requestTransactionManager();

        void requestTransactionDialog(TagVO vo);

    }
}
