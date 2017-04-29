package com.system.m4.views.home;

import com.system.m4.views.transaction.ItemVO;

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

        void setListTransactions(List<ItemVO> list);
    }

    /**
     *
     */
    interface Presenter {

        void requestListTransaction();
    }
}
