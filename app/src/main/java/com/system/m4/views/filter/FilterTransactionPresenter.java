package com.system.m4.views.filter;

import com.system.m4.R;
import com.system.m4.businness.PaymentTypeBusinness;
import com.system.m4.businness.TagBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
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

    private final FilterTransactionContract.View mView;

    private final FilterTransactionVO mVo;

    FilterTransactionPresenter(FilterTransactionContract.View view) {
        this.mView = view;
        this.mVo = new FilterTransactionVO();
    }

    @Override
    public void requestTagDialog() {

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                mView.showTagsDialog(TagVO.asList(list));
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestPaymentDateEndDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        mView.showPaymentDateEndDialog(date);
    }

    @Override
    public void requestPaymentDateStartDialog(String text) {

        Date date;
        if (text.isEmpty() || text.equals(Constants.EMPTY_FIELD)) {
            date = Calendar.getInstance().getTime();
        } else {
            date = JavaUtils.DateUtil.parse(text, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY);
        }

        mView.showPaymentDateStartDialog(date);
    }

    @Override
    public void requestPaymentTypeDialog() {

        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                mView.showPaymentTypeDialog(PaymentTypeVO.asList(list));
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void clearPaymentDateEnd() {
        mVo.setPaymentDateEnd(null);
    }

    @Override
    public void clearPaymentDateStart() {
        mVo.setPaymentDateStart(null);
    }

    @Override
    public void clearPaymentType() {
        mVo.setPaymentType(null);
    }

    @Override
    public void clearTag() {
        mVo.setTag(null);
    }

    @Override
    public void setPaymentDateEnd(int year, int month, int dayOfMonth) {
        Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
        mView.setPaymentDateEnd(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
    }

    @Override
    public void setPaymentDateStart(int year, int month, int dayOfMonth) {
        Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
        mView.setPaymentDateStart(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
    }

    @Override
    public void setPaymentType(PaymentTypeVO vo) {
        mVo.setPaymentType(vo);
        mView.setPaymentType(vo.getName());
    }

    @Override
    public void setTag(TagVO vo) {
        mVo.setTag(vo);
        mView.setTag(vo.getName());
    }

    @Override
    public void validateForm() {

        if (JavaUtils.ClassUtil.isEmpty(mVo.getTag())) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_tag);
        } else if (JavaUtils.ClassUtil.isEmpty(mVo.getPaymentType())) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_type);
        } else if (mVo.getPaymentDateStart() == null) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_date_start);
        } else if (mVo.getPaymentDateEnd() == null) {
            mView.showError(R.string.system_error_required_field, R.string.transaction_payment_date_end);
        }
    }
}