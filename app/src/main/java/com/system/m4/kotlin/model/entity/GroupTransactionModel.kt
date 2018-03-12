package com.system.m4.kotlin.model.entity

import com.google.gson.annotations.Expose
import com.system.m4.kotlin.data.GenericRepository

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

data class GroupTransactionModel(

        @Expose
        override var key: String? = null,

        @Expose
        var listPaymentType: List<String>? = null

) : GenericRepository.Model
