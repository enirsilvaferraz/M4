package com.system.m4.kotlin.group

import com.google.gson.annotations.Expose

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

class GroupTransactionDTO {

    @Expose
    var key: String? = null

    @Expose
    var listPaymentType: List<String>? = null
}
