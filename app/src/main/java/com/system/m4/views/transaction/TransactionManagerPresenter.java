package com.system.m4.views.transaction;

import com.system.m4.infrastructure.JavaUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Enir on 21/04/2017.
 */

public class TransactionManagerPresenter implements TransactionManagerContract.Presenter {

    private final TransactionManagerContract.View view;

    public TransactionManagerPresenter(TransactionManagerContract.View view) {
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
    public void requestTagList() {

        List<String> list = new ArrayList<>();
        list.add("Moradia");
        list.add("Aluguel");
        list.add("Celular");
        list.add("Internet");
        list.add("Automovel");
        list.add("Seguro");

        view.showTagsList(list);
    }

    @Override
    public void requestPaymentTypeList() {

        List<String> list = new ArrayList<>();
        list.add("Nubank");
        list.add("Dinheiro");
        list.add("Itaucard");
        list.add("Transferência Itaú");
        list.add("Transferência Bradesco");

        view.showPaymentTypeList(list);
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
