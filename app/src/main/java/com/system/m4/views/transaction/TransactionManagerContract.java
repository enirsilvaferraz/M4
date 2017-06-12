package com.system.m4.views.transaction;

import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOInterface;

import java.util.Date;

/**
 * Created by Enir on 21/04/2017.
 * Contrato para TransactionManager
 */

interface TransactionManagerContract {

    interface View {

        void configureModel(Transaction transaction);

        void setPaymentDate(String value);

        void setPurchaseDate(String value);

        void setValue(String value);

        void setTags(String value);

        void setPaymentType(String value);

        void setContent(String value);

        void showValueDialog(Double value);

        void showContentDialog(String value);

        void showPaymentDateDialog(Date date);

        void showPurchaseDateDialog(Date date);

        void dismissDialog(VOInterface vo);

        void showError(String error);

        void showSuccessMessage(int template, int param);

        void showError(int template, int param);
    }

    interface Presenter {

        void setPaymentDate(Date date);

        void setPurchaseDate(Date date);

        void setValue(Double value);

        void setTags(TagVO tagVO);

        void setPaymentType(PaymentTypeVO paymentTypeVO);

        void setContent(String content);

        void requestValueDialog(String text);

        void requestContentDialog(String text);

        void requestPaymentDateDialog(String text);

        void requestPurchaseDateDialog(String text);

        void clearContent();

        void clearPaymentType();

        void clearPrice();

        void clearPurchaseDate();

        void clearPaymentDate();

        void save();

        void init(Transaction transaction);
    }
}
