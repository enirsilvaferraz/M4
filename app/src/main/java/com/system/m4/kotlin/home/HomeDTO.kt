package com.system.m4.kotlin.home

import com.system.m4.views.vos.GroupTransactionVO
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.Transaction

/**
 * Created by enirs on 30/09/2017.
 */
class HomeDTO(val year: Int, val month: Int) {
    var listTransaction: ArrayList<Transaction>? = null
    var listTag: ArrayList<TagVO>? = null
    var listPaymentType: ArrayList<PaymentTypeVO>? = null
    var listGroup: ArrayList<GroupTransactionVO>? = null
}