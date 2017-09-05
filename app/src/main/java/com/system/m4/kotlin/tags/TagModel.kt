package com.system.m4.kotlin.tags

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by enirs on 30/08/2017.
 * Data Tag
 */
class TagModel() : Parcelable {

    @SerializedName("key")
    var key: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("parentKey")
    var parentKey: String? = null

    @SerializedName("children")
    var children: HashMap<String, TagModel>? = null

    var child: ArrayList<TagModel>? = null

    constructor(source: Parcel) : this(){
        source.toString()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        val TAG: String = "TagModel"

        @JvmField
        val CREATOR: Parcelable.Creator<TagModel> = object : Parcelable.Creator<TagModel> {
            override fun createFromParcel(source: Parcel): TagModel = TagModel(source)
            override fun newArray(size: Int): Array<TagModel?> = arrayOfNulls(size)
        }
    }
}