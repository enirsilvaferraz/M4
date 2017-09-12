package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class ListTransactionVO {

    private List<Transaction> transactions;
    private GroupTransactionVO group;
    private List<TagSummaryVO> tagSummary;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
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
}
