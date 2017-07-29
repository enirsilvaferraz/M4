package com.system.m4.views.home;

import com.system.m4.views.vos.TagVO;

/**
 * Created by eferraz on 29/07/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestTransactionManager() {
        mView.requestTransactionManagerDialog(); // Colocar PLaceholder e remover if list == null do adapter
    }

    @Override
    public void requestTransactionDialog(TagVO vo) {
        mView.showTransactionDialog(vo);
    }

}
