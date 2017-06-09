package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class ListTransactionVO {

    private List<TransactionVO> transactions;
    private GroupTransactionVO group;

    public List<TransactionVO> getTransactions() {
        return transactions;
    }

    public GroupTransactionVO getGroup() {
        return group;
    }

    public void setTransactions(List<TransactionVO> transactions) {
        this.transactions = transactions;
    }

    public void setGroup(GroupTransactionVO group) {
        this.group = group;
    }
}
