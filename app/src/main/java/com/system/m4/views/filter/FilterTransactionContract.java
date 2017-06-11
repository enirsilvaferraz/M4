package com.system.m4.views.filter;

import android.support.annotation.StringRes;

import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

interface FilterTransactionContract {

    interface View {

        void setMonth(String value);

        void setYear(String value);

        void setPaymentType(String value);

        void setTag(String value);

        void showTagsDialog(List<TagVO> list);

        void showPaymentTypeDialog(List<PaymentTypeVO> list);

        void showYearDialog(Integer year);

        void showMonthDialog(Integer month);

        void showError(String error);

        void showError(@StringRes int template, @StringRes int... params);

        void dismissDialog(FilterTransactionVO vo);
    }

    interface Presenter {

        void init();

        void requestTagDialog();

        void requestMonthDialog(String text);

        void requestYearDialog(String text);

        void requestPaymentTypeDialog();

        void clearMonth();

        void clearYear();

        void clearPaymentType();

        void clearTag();

        void setMonth(Integer month);

        void setYear(Integer year);

        void setPaymentType(PaymentTypeVO vo);

        void setTag(TagVO vo);

        void done();

        void delete();
    }
}
