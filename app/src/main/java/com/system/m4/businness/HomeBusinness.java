package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.views.vos.GroupTransactionVO;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

import java.util.Calendar;
import java.util.List;

/**
 * Created by eferraz on 10/06/17.
 * For M4
 */

public class HomeBusinness {

    public static void findTransactions(int year, int month, final BusinnessListener.OnSingleResultListener<ListTransactionVO> listener) {

        BusinnessObserver observer = new BusinnessObserver(year, month, listener);

        TransactionBusinness.findByFilter(year, month, observer);
        TagBusinness.findAll(observer);
        PaymentTypeBusinness.findAll(observer);
        GroupTransactionBusinness.findAll(observer);
        TransactionBusinness.findFixed(observer);
    }

    private static class BusinnessObserver implements BusinnessListener.OnMultiResultListenner {

        private final BusinnessListener.OnSingleResultListener<ListTransactionVO> listener;
        private final int year;
        private final int month;

        private List<Transaction> listTransaction;
        private List<Transaction> listFixedTransaction;
        private List<TagVO> listTag;
        private List<PaymentTypeVO> listPaymentType;
        private List<GroupTransactionVO> listGroup;

        BusinnessObserver(int year, int month, BusinnessListener.OnSingleResultListener<ListTransactionVO> listener) {
            this.year = year;
            this.month = month;
            this.listener = listener;
        }

        @Override
        public void onSuccess(List list, int call) {

            if (call == Constants.CALL_TRANSACTION_BY_FILTER) {
                listTransaction = list;

            } else if (call == Constants.CALL_TRANSACTION_FIXED) {
                listFixedTransaction = list;

            } else if (call == Constants.CALL_TAG_FINDALL) {
                listTag = list;

            } else if (call == Constants.CALL_PAYMENTTYPE_FINDALL) {
                listPaymentType = list;

            } else if (call == Constants.CALL_GROUP_FINDALL) {
                listGroup = list;

            }

            configureList(listTransaction, listFixedTransaction, listTag, listPaymentType, listGroup);
        }

        @Override
        public void onError(Exception e) {
            listener.onError(e);
        }

        private void configureList(List<Transaction> listTransaction, List<Transaction> listFixedTransaction, List<TagVO> listTag, List<PaymentTypeVO> listPaymentType, List<GroupTransactionVO> listGroup) {

            if (listTransaction != null && listTag != null && listPaymentType != null && listGroup != null) {

                if (listFixedTransaction != null) {

                    for (Transaction fixed : listFixedTransaction) {

                        if (!listTransaction.contains(fixed)) {

                            Calendar instance = Calendar.getInstance();
                            instance.setTime(fixed.getPaymentDate());
                            instance.set(Calendar.MONTH, month);
                            instance.set(Calendar.YEAR, year);
                            fixed.setPaymentDate(instance.getTime());

                            listTransaction.add(fixed);

                        } else {
                            Transaction transaction = listTransaction.get(listTransaction.indexOf(fixed));
                            transaction.setPinned(true);
                            transaction.setApproved(true);
                        }
                    }
                }

                for (Transaction transaction : listTransaction) {
                    ConverterUtils.fillTransaction(transaction, listTag, listPaymentType);
                }

                ListTransactionVO listTransactionVO = new ListTransactionVO();
                listTransactionVO.setTransactions(listTransaction);

                if (!listGroup.isEmpty()) {
                    listTransactionVO.setGroup(ConverterUtils.fillGroupTransaction(listGroup.get(0), listPaymentType));
                }

                listener.onSuccess(listTransactionVO);
            }
        }
    }
}
