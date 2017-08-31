package com.system.m4.kotlin.transaction

/**
 * Created by eferraz on 27/08/17.
 * DTO for Transction
 */
data class TransactionDTO constructor(
        var key: String,
        var paymentDate: String,
        var purchaseDate: String,
        var price: Double,
        var tag: String,
        var paymentType: String,
        var content: String
)