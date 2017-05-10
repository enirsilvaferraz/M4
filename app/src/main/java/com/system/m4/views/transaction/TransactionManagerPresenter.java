package com.system.m4.views.transaction;

import android.text.TextUtils;

import com.system.m4.R;
import com.system.m4.businness.PaymentTypeBusinness;
import com.system.m4.businness.TagBusinness;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Enir on 21/04/2017.
 * Presenter
 */

class TransactionManagerPresenter implements TransactionManagerContract.Presenter {

    private final TransactionManagerContract.View view;

    private TransactionVO mVO;

    TransactionManagerPresenter(TransactionManagerContract.View view) {
        this.view = view;
        this.mVO = new TransactionVO();
    }

    @Override
    public void setPaymentDate(String date) {
        mVO.setPaymentDate(date);
        view.setPaymentDate(JavaUtils.StringUtil.formatEmpty(date));
    }

    @Override
    public void setPurchaseDate(String date) {
        mVO.setPurchaseDate(date);
        view.setPurchaseDate(JavaUtils.StringUtil.formatEmpty(date));
    }

    @Override
    public void setValue(String value) {
        mVO.setPrice(value);
        view.setValue(JavaUtils.StringUtil.formatEmpty(JavaUtils.NumberUtil.currencyFormat(value)));
    }

    @Override
    public void init(TransactionVO transactionVO) {
        mVO = transactionVO;
        view.configureModel(transactionVO);
    }

    @Override
    public void setTags(TagVO tagVO) {
        mVO.setTag(tagVO);
        view.setTags(JavaUtils.StringUtil.formatEmpty(tagVO.getName()));
    }

    @Override
    public void setPaymentType(PaymentTypeVO paymentTypeVO) {
        if (paymentTypeVO != null) {
            mVO.setPaymentType(paymentTypeVO);
            view.setPaymentType(JavaUtils.StringUtil.formatEmpty(paymentTypeVO.getName()));
        }
    }

    @Override
    public void setContent(String content) {
        mVO.setContent(content);
        view.setContent(JavaUtils.StringUtil.formatEmpty(content));
    }

    @Override
    public void requestTagDialog() {
        view.showTagsDialog();
        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                view.configureTagList(TagVO.asList(list));
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestPaymentTypeDialog() {
        view.showPaymentTypeDialog();
        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                view.configurePaymentTypeList(PaymentTypeVO.asList(list));
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
        mVO.setContent(null);
    }

    @Override
    public void clearPaymentType() {
        mVO.setPaymentType(null);
    }

    @Override
    public void clearTag() {
        mVO.setTag(null);
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
    public void clearPaymentDateDialog() {
        mVO.setContent(null);
    }

    @Override
    public void save() {

        if (JavaUtils.ClassUtil.isEmpty(mVO.getTag())) {
            view.showError(R.string.system_error_required_field, R.string.transaction_tag);
        } else if (JavaUtils.ClassUtil.isEmpty(mVO.getPaymentType())) {
            view.showError(R.string.system_error_required_field, R.string.transaction_payment_type);
        } else if (TextUtils.isEmpty(mVO.getPaymentDate())) {
            view.showError(R.string.system_error_required_field, R.string.transaction_payment_date);
        } else if (TextUtils.isEmpty(mVO.getPurchaseDate())) {
            view.showError(R.string.system_error_required_field, R.string.transaction_purchase_date);
        } else if (TextUtils.isEmpty(mVO.getPrice())) {
            view.showError(R.string.system_error_required_field, R.string.transaction_price);
        } else {

            TransactionBusinness.save(mVO, new BusinnessListener.OnPersistListener() {

                @Override
                public void onSuccess() {
                    view.dismissDialog();
                }

                @Override
                public void onError(Exception e) {
                    view.showError(e.getMessage());
                }
            });
        }
    }

    @Override
    public void saveTag(TagVO vo) {
        TagBusinness.save(new TagDTO(vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                view.showSuccessMessage(R.string.system_message_saved, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void deleteTag(TagVO vo) {
        TagBusinness.delete(new TagDTO(vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                view.showSuccessMessage(R.string.system_message_deleted, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void savePaymentType(PaymentTypeVO vo) {
        PaymentTypeBusinness.save(new PaymentTypeDTO(vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                view.showSuccessMessage(R.string.system_message_saved, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void deletePaymentType(PaymentTypeVO vo) {
        PaymentTypeBusinness.delete(new PaymentTypeDTO(vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                view.showSuccessMessage(R.string.system_message_deleted, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }
}