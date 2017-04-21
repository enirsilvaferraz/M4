package com.system.m4.views.transaction;

import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Enir on 21/04/2017.
 * Presenter
 */

class TransactionManagerPresenter implements TransactionManagerContract.Presenter {

    private final TransactionManagerContract.View view;

    TransactionManagerPresenter(TransactionManagerContract.View view) {
        this.view = view;
    }

    @Override
    public void setPaymentDate(int year, int month, int dayOfMonth) {
        Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
        view.setPaymentDate(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
    }

    @Override
    public void setPurchaseDate(int year, int month, int dayOfMonth) {
        Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
        view.setPurchaseDate(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
    }

    @Override
    public void setValue(String value) {
        view.setValue(JavaUtils.NumberUtil.currencyFormat(value));
    }

    @Override
    public void requestTagDialog() {

        List<String> list = new ArrayList<>();
        list.add("Moradia");
        list.add("Aluguel");
        list.add("Celular");
        list.add("Internet");
        list.add("Automovel");
        list.add("Seguro");

        view.showTagsDialog(list);
    }

    @Override
    public void requestPaymentTypeDialog() {

        List<String> list = new ArrayList<>();
        list.add("Nubank");
        list.add("Dinheiro");
        list.add("Itaucard");
        list.add("Transferência Itaú");
        list.add("Transferência Bradesco");

        view.showPaymentTypeDialog(list);
    }

    @Override
    public void requestValueDialog(String text) {
        Double value = text.isEmpty() || text.equals(Constants.EMPTY_FIELD) ? null : JavaUtils.NumberUtil.removeFormat(text);
        view.showValueDialog(value);
    }

    @Override
    public void requestContentDialog(String text) {
        if (text.equals(Constants.EMPTY_FIELD)) {
            text = null;
        }
        view.showContentDialog(text);
    }

    @Override
    public void requestPaymentDateDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        view.showPaymentDateDialog(date);
    }

    @Override
    public void requestPurchaseDateDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        view.showPurchaseDateDialog(date);
    }

    @Override
    public void clearContent() {

    }

    @Override
    public void clearPaymentType() {

    }

    @Override
    public void clearTagDialog() {

    }

    @Override
    public void clearValueDialog() {

    }

    @Override
    public void clearPurchaseDateDialog() {

    }

    @Override
    public void clearPaymentDateDialog() {

    }

    @Override
    public void validateForm() {
        view.dismissDialog();
    }

    @Override
    public void setTags(String itemName) {
        view.setTags(itemName);
    }

    @Override
    public void setPaymentType(String itemName) {
        view.setPaymentType(itemName);
    }

    @Override
    public void setContent(String content) {
        view.setContent(content);
    }
}
