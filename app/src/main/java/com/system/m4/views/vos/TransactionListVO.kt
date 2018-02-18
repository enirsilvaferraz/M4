package com.system.m4.views.vos

data class TransactionListVO(var transactions: List<TransactionVO>, var amount: Double, val refound: Double, val valueNotPaid: Double)