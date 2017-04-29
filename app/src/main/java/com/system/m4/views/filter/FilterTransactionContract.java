package com.system.m4.views.filter;

import java.util.Date;
import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 */

public interface FilterTransactionContract {

    interface View {

        void setPaymentDateStart(String value);

        void setPaymentDateEnd(String value);

        void setTag(String value);

        void setPaymentType(String value);

        void showTagsDialog(List<String> list);

        void showPaymentTypeDialog(List<String> list);

        void showPaymentDateStartDialog(Date date);

        void showPaymentDateEndDialog(Date date);

        void dismissDialog();

        void showError(String error);
    }

    interface Presenter {

        void setPaymentDateStart(int year, int month, int dayOfMonth);

        void setPaymentDateEnd(int year, int month, int dayOfMonth);

        void setTags(String itemName);

        void setPaymentType(String itemName);

        void requestTagDialog();

        void requestPaymentTypeDialog();

        void requestPaymentDateStartDialog(String text);

        void requestPaymentDateEndDialog(String text);

        void clearPaymentType();

        void clearTag();

        void clearPaymentDateStart();

        void clearPaymentDateEnd();

        void validateForm();
    }

}
