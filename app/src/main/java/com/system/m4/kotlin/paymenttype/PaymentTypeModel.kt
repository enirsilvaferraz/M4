package com.system.m4.kotlin.paymenttype

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by eferraz on 27/08/17.
 * DTO for PaymentType
 */
class PaymentTypeModel() : Parcelable {

    @Expose
    @SerializedName("key")
    var key: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("color")
    var color: String? = null

    constructor(source: Parcel) : this() {
        source.toString()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PaymentTypeModel> = object : Parcelable.Creator<PaymentTypeModel> {
            override fun createFromParcel(source: Parcel): PaymentTypeModel = PaymentTypeModel(source)
            override fun newArray(size: Int): Array<PaymentTypeModel?> = arrayOfNulls(size)
        }
        val TAG: String = "PaymentTypeModel"
    }
}