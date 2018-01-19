package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class HomeVO {

    private List<TransactionVO> transactions1Q;
    private List<TransactionVO> transactions2Q;
    private double amount1Q;
    private double amount2Q;

    private GroupTransactionVO group;
    private List<TagSummaryVO> tagSummary;
    private List<TransactionVO> pendingTransaction;


    public List<TransactionVO> getTransactions1Q() {
        return transactions1Q;
    }

    public void setTransactions1Q(List<TransactionVO> transactions1Q) {
        this.transactions1Q = transactions1Q;
    }

    public List<TransactionVO> getTransactions2Q() {
        return transactions2Q;
    }

    public void setTransactions2Q(List<TransactionVO> transactions2Q) {
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

    public double getAmount1Q() {
        return amount1Q;
    }

    public void setAmount1Q(double amount1Q) {
        this.amount1Q = amount1Q;
    }

    public double getAmount2Q() {
        return amount2Q;
    }

    public void setAmount2Q(double amount2Q) {
        this.amount2Q = amount2Q;
    }
}

