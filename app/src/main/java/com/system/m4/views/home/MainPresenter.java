package com.system.m4.views.home;

import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.TagVO;

import java.util.Calendar;

/**
 * Created by eferraz on 29/07/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        configureTitle(1);
    }

    @Override
    public void requestTransactionManager() {
        mView.requestTransactionManagerDialog(); // Colocar PLaceholder e remover if list == null do adapter
    }

    @Override
    public void requestTransactionDialog(TagVO vo) {
        mView.showTransactionDialog(vo);
    }

    @Override
    public void configureTitle(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, position - MainPageAdapter.PAGE_MIDDLE);
        mView.setMainTitle(JavaUtils.DateUtil.format(calendar.getTime(), JavaUtils.DateUtil.MMMM_DE_YYYY));
    }

}
