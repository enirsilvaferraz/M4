package com.system.m4.views.vos;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class HomeVO {

    public HashMap<PaymentTypeVO, TransactionListVO> groupMap;
    private TransactionListVO transactions1Q;
    private TransactionListVO transactions2Q;
    private GroupTransactionVO group;
    private List<TagSummaryVO> tagSummary;
    private List<TransactionVO> pendingTransaction;

    @NotNull
    public HashMap<PaymentTypeVO, TransactionListVO> getGroupMap() {
        return groupMap;
    }

    public void setGroupMap(@NotNull HashMap<PaymentTypeVO, TransactionListVO> groupMap) {
        this.groupMap = groupMap;
    }

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

    public GroupTransactionVO getGroup() {
        return group;
    }

    public void setGroup(GroupTransactionVO group) {
        this.group = group;
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
}

