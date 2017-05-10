package com.system.m4.views.transaction;

import com.system.m4.R;
import com.system.m4.businness.PaymentTypeBusinness;
import com.system.m4.businness.TagBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
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

    private final TransactionVO mVO;
    private final TransactionManagerContract.View view;
    private TransactionDTO mDTO;

    TransactionManagerPresenter(TransactionManagerContract.View view) {
        this.view = view;
        this.mVO = new TransactionVO();
        this.mDTO = new TransactionDTO();
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
    public void validateForm() {
        view.dismissDialog();
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

    @Override
    public void init(TransactionVO transactionVO) {
        mDTO = new TransactionDTO(transactionVO);
        view.configureModel(transactionVO);
    }

    @Override
    public void setTags(TagVO tagVO) {
        mDTO.setTag(new TagDTO(tagVO));
        view.setTags(JavaUtils.StringUtil.formatEmpty(tagVO.getName()));
    }

    @Override
    public void setPaymentType(PaymentTypeVO paymentTypeVO) {
        if (paymentTypeVO != null) {
            mDTO.setPaymentType(new PaymentTypeDTO(paymentTypeVO));
            view.setPaymentType(JavaUtils.StringUtil.formatEmpty(paymentTypeVO.getName()));
        }
    }

    @Override
    public void setContent(String content) {
        view.setContent(JavaUtils.StringUtil.formatEmpty(content));
    }
}
