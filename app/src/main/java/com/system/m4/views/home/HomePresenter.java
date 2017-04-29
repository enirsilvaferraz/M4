package com.system.m4.views.home;

import com.system.m4.views.transaction.ItemVO;

import java.util.ArrayList;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void requestListTransaction() {

        ArrayList<ItemVO> list = new ArrayList<>();
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));
        list.add(new ItemVO("", "", "", "", "", ""));

        view.setListTransactions(list);
    }
}
