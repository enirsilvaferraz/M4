package com.system.m4.views.filter;

import android.text.TextUtils;

import com.system.m4.R;
import com.system.m4.businness.FilterTransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SimpleItemListVO;
import com.system.m4.views.vos.TagVO;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class FilterTransactionPresenter implements FilterTransactionContract.Presenter {

    private final FilterTransactionContract.View mView;

    private FilterTransactionVO mVo;

    FilterTransactionPresenter(FilterTransactionContract.View view) {
        this.mView = view;
        this.mVo = new FilterTransactionVO();
    }

    @Override
    public void init() {

        FilterTransactionBusinness.find(new BusinnessListener.OnSingleResultListener<FilterTransactionVO>() {

            @Override
            public void onSuccess(FilterTransactionVO vo) {
                if (vo != null) {
                    mVo = vo;
                    mView.setYear(JavaUtils.StringUtil.formatEmpty(vo.getYear()));
                    mView.setMonth(JavaUtils.StringUtil.formatEmpty(mView.getStringArray(R.array.months)[vo.getMonth()]));
                    mView.setTag(JavaUtils.StringUtil.formatEmpty(vo.getTag() != null ? vo.getTag().getName() : null));
                    mView.setPaymentType(JavaUtils.StringUtil.formatEmpty(vo.getPaymentType() != null ? vo.getPaymentType().getName() : null));
                }
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void clearMonth() {
        mVo.setMonth(null);
    }

    @Override
    public void clearYear() {
        mVo.setYear(null);
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
    public void setMonth(SimpleItemListVO month) {
        mVo.setMonth(Integer.valueOf(month.getKey()));
        mView.setMonth(month.getName());
    }

    @Override
    public void setYear(SimpleItemListVO year) {
        mVo.setYear(Integer.valueOf(year.getKey()));
        mView.setYear(year.getName());
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
    public void done() {
        if (isSaveMode()) {
            FilterTransactionBusinness.save(ConverterUtils.fromFilterTransaction(mVo), new BusinnessListener.OnPersistListener() {

                @Override
                public void onSuccess(DTOAbs dto) {
                    mView.dismissDialog(null);
                }

                @Override
                public void onError(Exception e) {
                    mView.showError(e.getMessage());
                }
            });
        } else if (!TextUtils.isEmpty(mVo.getKey())) {
            delete();
        } else {
            mView.dismissDialog(null);
        }
    }

    private boolean isSaveMode() {
        return mVo.getYear() != null || mVo.getMonth() != null ||
                (mVo.getTag() != null && mVo.getTag().getKey() != null) ||
                (mVo.getPaymentType() != null && mVo.getPaymentType().getKey() != null);
    }

    @Override
    public void delete() {

        FilterTransactionBusinness.delete(ConverterUtils.fromFilterTransaction(mVo), new BusinnessListener.OnPersistListener() {

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