package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class ListTransactionVO {

    private List<TransactionVO> transactions;
    private GroupTransactionVO group;
    private List<TagSummaryVO> tagSummary;
    private List<TransactionVO> pendingTransaction;

    public List<TransactionVO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionVO> transactions) {
        this.transactions = transactions;
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
