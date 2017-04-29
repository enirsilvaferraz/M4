package com.system.m4.views.filter;

import com.system.m4.businness.transaction.TransactionManagerBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class FilterTransactionPresenter implements FilterTransactionContract.Presenter {

    private final FilterTransactionContract.View view;

    FilterTransactionPresenter(FilterTransactionContract.View view) {
        this.view = view;
    }

    @Override
    public void setPaymentDateStart(int year, int month, int dayOfMonth) {
        Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
        view.setPaymentDateStart(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
    }

    @Override
    public void setPaymentDateEnd(int year, int month, int dayOfMonth) {
        Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
        view.setPaymentDateEnd(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
    }

    @Override
    public void requestTagDialog() {

        TransactionManagerBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner() {

            @Override
            public void onSuccess(List<String> list) {
                view.showTagsDialog(list);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestPaymentTypeDialog() {

        TransactionManagerBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner() {

            @Override
            public void onSuccess(List<String> list) {
                view.showPaymentTypeDialog(list);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestPaymentDateStartDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        view.showPaymentDateStartDialog(date);
    }

    @Override
    public void requestPaymentDateEndDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        view.showPaymentDateEndDialog(date);
    }

    @Override
    public void clearPaymentType() {

    }

    @Override
    public void clearTag() {

    }

    @Override
    public void clearPaymentDateStart() {

    }

    @Override
    public void clearPaymentDateEnd() {

    }

    @Override
    public void validateForm() {
        view.dismissDialog();
    }

    @Override
    public void persistFilter() {

    }

    @Override
    public void setTags(String itemName) {
        view.setTag(itemName);
    }

    @Override
    public void setPaymentType(String itemName) {
        view.setPaymentType(itemName);
    }

}
