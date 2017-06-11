package com.system.m4.views.filter;

import android.support.annotation.StringRes;

import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.SimpleItemListVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;

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

        void showYearDialog();

        void showMonthDialog();

        void showError(String error);

        void showError(@StringRes int template, @StringRes int... params);

        void dismissDialog(FilterTransactionVO vo);

        String[] getStringArray(int months);
    }

    interface Presenter {

        void init();

        void clearMonth();

        void clearYear();

        void clearPaymentType();

        void clearTag();

        void setMonth(SimpleItemListVO month);

        void setYear(SimpleItemListVO year);

        void setPaymentType(PaymentTypeVO vo);

        void setTag(TagVO vo);

        void done();

        void delete();
    }
}
