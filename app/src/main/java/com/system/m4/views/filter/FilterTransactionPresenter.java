package com.system.m4.views.filter;

import com.system.m4.businness.FilterTransactionBusinness;
import com.system.m4.businness.PaymentTypeBusinness;
import com.system.m4.businness.TagBusinness;
import com.system.m4.businness.dtos.PaymentTypeDTO;
import com.system.m4.businness.dtos.TagDTO;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class FilterTransactionPresenter implements FilterTransactionContract.Presenter {

    private final FilterTransactionContract.View view;
    private final FilterTransactionVO mVo;

    FilterTransactionPresenter(FilterTransactionContract.View view) {
        this.view = view;
        this.mVo = new FilterTransactionVO();
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
        mVo.setPaymentType(null);
    }

    @Override
    public void clearTag() {
        mVo.setTags(null);
    }

    @Override
    public void clearPaymentDateStart() {
        mVo.setPaymentDateStart(null);
    }

    @Override
    public void clearPaymentDateEnd() {
        mVo.setPaymentDateEnd(null);
    }

    @Override
    public void validateForm() {
        view.dismissDialog();
    }

    @Override
    public void persistFilter() {

        FilterTransactionBusinness.persistFilter(mVo, new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
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
