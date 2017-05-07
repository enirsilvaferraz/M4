package com.system.m4.views.filter;

import android.support.annotation.StringRes;

import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;

import java.util.Date;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

interface FilterTransactionContract {

    interface View {

        void setPaymentDateEnd(String value);

        void setPaymentDateStart(String value);

        void setPaymentType(String value);

        void setTag(String value);

        void showTagsDialog(List<TagVO> list);

        void showPaymentTypeDialog(List<PaymentTypeVO> list);

        void showPaymentDateStartDialog(Date date);

        void showPaymentDateEndDialog(Date date);

        void showError(String error);

        void showError(@StringRes int template, @StringRes int... params);
    }

    interface Presenter {

        void requestTagDialog();

        void requestPaymentDateEndDialog(String text);

        void requestPaymentDateStartDialog(String text);

        void requestPaymentTypeDialog();

        void clearPaymentDateEnd();

        void clearPaymentDateStart();

        void clearPaymentType();

        void clearTag();

        void setPaymentDateEnd(int year, int month, int dayOfMonth);

        void setPaymentDateStart(int year, int month, int dayOfMonth);

        void setPaymentType(String itemName);

        void setTags(String itemName);

        void validateForm();

        void saveTag(String name);

        void deleteTag(String key);

        void savePaymentType(String name);

        void deletePaymentType(String key);
    }
}
