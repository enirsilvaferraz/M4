package com.system.m4.views.transaction;

import com.system.m4.businness.PaymentTypeBusinness;
import com.system.m4.businness.TagBusinness;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;

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
    public void setPaymentDate(String date) {
        view.setPaymentDate(JavaUtils.StringUtil.formatEmpty(date));
    }

    @Override
    public void setPurchaseDate(String date) {
        view.setPurchaseDate(JavaUtils.StringUtil.formatEmpty(date));
    }

    @Override
    public void setValue(String value) {
        view.setValue(JavaUtils.StringUtil.formatEmpty(JavaUtils.NumberUtil.currencyFormat(value)));
    }

    @Override
    public void requestTagDialog() {

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                view.showTagsDialog(TagVO.asList(list));
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestPaymentTypeDialog() {

        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                view.showPaymentTypeDialog(PaymentTypeVO.asList(list));
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
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
    public void saveTag(String name) {

    }

    @Override
    public void deleteTag(String key) {

    }

    @Override
    public void savePaymentType(String name) {

    }

    @Override
    public void deletePaymentType(String key) {

    }

    @Override
    public void setTags(String itemName) {
        view.setTags(JavaUtils.StringUtil.formatEmpty(itemName));
    }

    @Override
    public void setPaymentType(String itemName) {
        view.setPaymentType(JavaUtils.StringUtil.formatEmpty(itemName));
    }

    @Override
    public void setContent(String content) {
        view.setContent(JavaUtils.StringUtil.formatEmpty(content));
    }
}
