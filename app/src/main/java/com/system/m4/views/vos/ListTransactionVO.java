package com.system.m4.views.vos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 28/05/17.
 * For M4
 */

public class ListTransactionVO {

    private List<TransactionVO> currentList;

    private List<TransactionVO> futureList;

    public ListTransactionVO() {
        currentList = new ArrayList<>();
        futureList = new ArrayList<>();
    }

    public List<TransactionVO> getCurrentList() {
        return currentList;
    }

    public List<TransactionVO> getFutureList() {
        return futureList;
    }
}
