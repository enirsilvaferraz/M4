package com.system.m4.views.vos;

import java.util.HashMap;
import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class HomeVO {

    private TransactionListVO transactions1Q;
    private TransactionListVO transactions2Q;
    private List<TagSummaryVO> tagSummary;
    private List<TransactionVO> pendingTransaction;
    private HashMap<PaymentTypeVO, TransactionListVO> groups;
    private double amount;

    public TransactionListVO getTransactions1Q() {
        return transactions1Q;
    }

    public void setTransactions1Q(TransactionListVO transactions1Q) {
        this.transactions1Q = transactions1Q;
    }

    public TransactionListVO getTransactions2Q() {
        return transactions2Q;
    }

    public void setTransactions2Q(TransactionListVO transactions2Q) {
        this.transactions2Q = transactions2Q;
    }

    public List<TagSummaryVO> getTagSummary() {
        return tagSummary;
    }

    public void setTagSummary(List<TagSummaryVO> tagSummary) {
        this.tagSummary = tagSummary;
    }

    public List<TransactionVO> getPendingTransaction() {
        return pendingTransaction;
    }

    public void setPendingTransaction(List<TransactionVO> pendingTransaction) {
        this.pendingTransaction = pendingTransaction;
    }

    public HashMap<PaymentTypeVO, TransactionListVO> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<PaymentTypeVO, TransactionListVO> groups) {
        this.groups = groups;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

