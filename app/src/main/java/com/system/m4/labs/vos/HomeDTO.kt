package com.system.m4.labs.vos

/**
 * Created by enirs on 30/09/2017.
 */
class HomeDTO(val year: Int, val month: Int) {
    var listTransaction: ArrayList<TransactionVO>? = null
    var listTag: ArrayList<TagVO>? = null
    var listPaymentType: ArrayList<PaymentTypeVO>? = null
    var listGroup: ArrayList<GroupTransactionVO>? = null
}