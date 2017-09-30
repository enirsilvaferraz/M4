package com.system.m4.kotlin.transaction

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by eferraz on 27/08/17.
 * DTO for Transction
 */
data class TransactionModel constructor(

        var key: String?,
        var paymentDate: String?,
        var purchaseDate: String?,
        var price: Double,
        var tag: String?,
        var paymentType: String?,
        var content: String?

) : Parcelable {

    constructor() : this(null, null, null, 0.0, null, null, null) {
        // Nothing to do
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(key)
        writeString(paymentDate)
        writeString(purchaseDate)
        writeDouble(price)
        writeString(tag)
        writeString(paymentType)
        writeString(content)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TransactionModel> = object : Parcelable.Creator<TransactionModel> {
            override fun createFromParcel(source: Parcel): TransactionModel = TransactionModel(source)
            override fun newArray(size: Int): Array<TransactionModel?> = arrayOfNulls(size)
        }
    }
}