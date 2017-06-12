package com.system.m4.views.transaction;

import android.text.TextUtils;

import com.system.m4.R;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Enir on 21/04/2017.
 * Presenter
 */

class TransactionManagerPresenter implements TransactionManagerContract.Presenter {

    private final TransactionManagerContract.View mView;

    private Transaction mVO;

    TransactionManagerPresenter(TransactionManagerContract.View mView) {
        this.mView = mView;
        this.mVO = new Transaction();
    }

    @Override
    public void setPaymentDate(Date date) {
        mVO.setPaymentDate(date);
        mView.setPaymentDate(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)));
    }

    @Override
    public void setPurchaseDate(Date date) {
        mVO.setPurchaseDate(date);
        mView.setPurchaseDate(JavaUtils.StringUtil.formatEmpty(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY)));
    }

    @Override
    public void setValue(Double value) {
        mVO.setPrice(value);
        mView.setValue(JavaUtils.StringUtil.formatEmpty(JavaUtils.NumberUtil.currencyFormat(value)));
    }

    @Override
    public void init(Transaction transaction) {
        mVO = transaction;
        if (TextUtils.isEmpty(mVO.getKey())) {
            mVO.setPaymentDate(Calendar.getInstance().getTime());
        }

        mView.configureModel(transaction);
    }

    @Override
    public void setTags(TagVO tagVO) {
        mVO.setTag(tagVO);
        mView.setTags(JavaUtils.StringUtil.formatEmpty(tagVO.getName()));
    }

    @Override
    public void setPaymentType(PaymentTypeVO paymentTypeVO) {
        if (paymentTypeVO != null) {
            mVO.setPaymentType(paymentTypeVO);
            mView.setPaymentType(JavaUtils.StringUtil.formatEmpty(paymentTypeVO.getName()));
        }
    }

    @Override
    public void setContent(String content) {
        mVO.setContent(content);
        mView.setContent(JavaUtils.StringUtil.formatEmpty(content));
    }

    @Override
    public void requestValueDialog(String text) {
        Double value = text.isEmpty() || text.equals(Constants.EMPTY_FIELD) ? null : JavaUtils.NumberUtil.removeFormat(text);
        mView.showValueDialog(value);
    }

    @Override
    public void requestContentDialog(String text) {
        if (text.equals(Constants.EMPTY_FIELD)) {
            text = null;
        }
        mView.showContentDialog(text);
    }

    @Override
    public void requestPaymentDateDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        mView.showPaymentDateDialog(date);
    }

    @Override
    public void requestPurchaseDateDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        mView.showPurchaseDateDialog(date);
    }

    @Override
    public void clearContent() {
        mVO.setContent(null);
    }

    @Override
    public void clearPaymentType() {
        mVO.setPaymentType(null);
    }

    @Override
    public void clearPrice() {
        mVO.setPrice(null);
    }

    @Override
    public void clearPurchaseDate() {
        mVO.setPurchaseDate(null);
    }

    @Override
    public void clearPaymentDate() {
        mVO.setPaymentDate(null);
    }

    @Override
    public void save() {

        if (JavaUtils.ClassUtil.isEmpty(mVO.getTag())) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_tag);
        } else if (JavaUtils.ClassUtil.isEmpty(mVO.getPaymentType())) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_type);
        } else if (mVO.getPaymentDate() == null) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_date);
        } else if (mVO.getPrice() == null) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_price);
        } else {

            TransactionBusinness.save(mVO, new BusinnessListener.OnPersistListener() {

                @Override
                public void onSuccess(DTOAbs dto) {
                    mView.dismissDialog(null);
                }

                @Override
                public void onError(Exception e) {
                    mView.showError(e.getMessage());
                }
            });
        }
    }
}