package com.system.m4.labs.vos

data class TransactionListVO(var transactions: List<TransactionVO>, var amount: Double, val refund: Double, val valueNotPaid: Double)