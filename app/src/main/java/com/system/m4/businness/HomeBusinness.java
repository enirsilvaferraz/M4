package com.system.m4.businness;

import android.support.annotation.NonNull;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.GroupTransactionVO;
import com.system.m4.views.vos.ListTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eferraz on 10/06/17.
 * For M4
 */

public class HomeBusinness {

    public static void findTransactions(final BusinnessListener.OnSingleResultListener<ListTransactionVO> listener) {

        FilterTransactionBusinness.find(new BusinnessListener.OnSingleResultListener<FilterTransactionVO>() {

            @Override
            public void onSuccess(FilterTransactionVO vo) {
                if (vo == null) {
                    vo = new FilterTransactionVO();
                    vo.setYear(Calendar.getInstance().get(Calendar.YEAR));
                    vo.setMonth(Calendar.getInstance().get(Calendar.MONTH));
                }
                findTransactions(vo, listener);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });
    }

    private static void findTransactions(final FilterTransactionVO filter, final BusinnessListener.OnSingleResultListener<ListTransactionVO> listener) {

        BusinnessObserver observer = new BusinnessObserver(filter, listener);

        TransactionBusinness.findByFilter(filter.getYear(), filter.getMonth(), observer);
        TagBusinness.findAll(observer);
        PaymentTypeBusinness.findAll(observer);
        GroupTransactionBusinness.findAll(observer);

        if ((filter.getYear() == Calendar.getInstance().get(Calendar.YEAR) && filter.getMonth() >= Calendar.getInstance().get(Calendar.MONTH))
                || (filter.getYear() > Calendar.getInstance().get(Calendar.YEAR))) {

            TransactionBusinness.findFixed(observer);
        }
    }

    private static class BusinnessObserver implements BusinnessListener.OnMultiResultListenner {

        private final BusinnessListener.OnSingleResultListener<ListTransactionVO> listener;
        private FilterTransactionVO filter;

        private List<Transaction> listTransaction;
        private List<Transaction> listFixedTransaction;
        private List<TagVO> listTag;
        private List<PaymentTypeVO> listPaymentType;
        private List<GroupTransactionVO> listGroup;

        BusinnessObserver(FilterTransactionVO filter, BusinnessListener.OnSingleResultListener<ListTransactionVO> listener) {
            this.filter = filter;
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
                            instance.set(Calendar.MONTH, filter.getMonth());
                            instance.set(Calendar.YEAR, filter.getYear());
                            fixed.setPaymentDate(instance.getTime());

                            listTransaction.add(fixed);

                        } else {
                            Transaction transaction = listTransaction.get(listTransaction.indexOf(fixed));
                            transaction.setPinned(true);
                            transaction.setApproved(true);
                        }
                    }
                }

                List<Transaction> listVo = filterTransaction(listTransaction, listTag, listPaymentType);

                ListTransactionVO listTransactionVO = new ListTransactionVO();
                listTransactionVO.setTransactions(listVo);

                if (!listGroup.isEmpty()) {
                    listTransactionVO.setGroup(ConverterUtils.fillGroupTransaction(listGroup.get(0), listPaymentType));
                }

                listener.onSuccess(listTransactionVO);
            }
        }

        @NonNull
        private List<Transaction> filterTransaction(List<Transaction> listTransaction, List<TagVO> listTag, List<PaymentTypeVO> listPaymentType) {

            List<Transaction> listVo = new ArrayList<>();

            for (Transaction vo : listTransaction) {
                if (filter != null) {
                    if ((filter.getTag() == null || vo.getTag().equals(filter.getTag())) &&
                            (filter.getPaymentType() == null || vo.getPaymentType().equals(filter.getPaymentType()))) {
                        listVo.add(ConverterUtils.fillTransaction(vo, listTag, listPaymentType));
                    }
                } else {
                    listVo.add(ConverterUtils.fillTransaction(vo, listTag, listPaymentType));
                }
            }
            return listVo;
        }
    }
}
