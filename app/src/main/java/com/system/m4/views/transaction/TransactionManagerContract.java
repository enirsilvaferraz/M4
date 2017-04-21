package com.system.m4.views.transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by Enir on 21/04/2017.
 * Contrato para TransactionManager
 */

public interface TransactionManagerContract {

    interface View {

        void setPaymentDate(String value);

        void setPurchaseDate(String value);

        void setValue(String value);

        void setTags(String value);

        void setPaymentType(String value);

        void setContent(String value);

        void showTagsDialog(List<String> list);

        void showPaymentTypeDialog(List<String> list);

        void showValueDialog(Double value);

        void showContentDialog(String value);

        void showPaymentDateDialog(Date date);

        void showPurchaseDateDialog(Date date);

        void dismissDialog();

        void showError(String error);
    }

    interface Presenter {

        void setPaymentDate(int year, int month, int dayOfMonth);

        void setPurchaseDate(int year, int month, int dayOfMonth);

        void setValue(String value);

        void setTags(String itemName);

        void setPaymentType(String itemName);

        void setContent(String content);

        void requestTagDialog();

        void requestPaymentTypeDialog();

        void requestValueDialog(String text);

        void requestContentDialog(String text);

        void requestPaymentDateDialog(String text);

        void requestPurchaseDateDialog(String text);

        void clearContent();

        void clearPaymentType();

        void clearTagDialog();

        void clearValueDialog();

        void clearPurchaseDateDialog();

        void clearPaymentDateDialog();

        void validateForm();
    }
}
