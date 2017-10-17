package com.system.m4.kotlin.transaction

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by eferraz on 27/08/17.
 * DTO for Transction
 */
data class TransactionModel constructor(

        @Expose
        @SerializedName("key")
        var key: String?,

        @Expose
        @SerializedName("paymentDate")
        var paymentDate: String?,

        @Expose
        @SerializedName("purchaseDate")
        var purchaseDate: String?,

        @Expose
        @SerializedName("price")
        var price: Double,

        @Expose
        @SerializedName("tag")
        var tag: String?,

        @Expose
        @SerializedName("paymentType")
        var paymentType: String?,

        @Expose
        @SerializedName("content")
        var content: String?,

        @Expose
        @SerializedName("fixed")
        var fixed: Boolean

) : Parcelable {
    constructor() : this(null, null, null, 0.0, null, null, null, false) {
        // Nothing to do
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt()
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
        writeInt((if (fixed) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TransactionModel> = object : Parcelable.Creator<TransactionModel> {
            override fun createFromParcel(source: Parcel): TransactionModel = TransactionModel(source)
            override fun newArray(size: Int): Array<TransactionModel?> = arrayOfNulls(size)
        }
    }
}