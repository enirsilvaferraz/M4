package com.system.m4.views.transaction;

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

        void showTagsList(List<String> list);

        void showPaymentTypeList(List<String> list);
    }

    interface Presenter {

        void setPaymentDate(int year, int month, int dayOfMonth);

        void setPurchaseDate(int year, int month, int dayOfMonth);

        void setValue(String value);

        void setTags(String itemName);

        void setPaymentType(String itemName);

        void setContent(String content);

        void requestTagList();

        void requestPaymentTypeList();
    }
}
