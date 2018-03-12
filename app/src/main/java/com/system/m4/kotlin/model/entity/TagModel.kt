package com.system.m4.kotlin.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.system.m4.kotlin.data.GenericRepository

/**
 * Created by enirs on 30/08/2017.
 * Data Tag
 */
class TagModel() : Parcelable, GenericRepository.Model {

    @Expose
    @SerializedName("key")
    override var key: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("parentKey")
    var parentKey: String? = null

    @Expose
    @SerializedName("showInManager")
    var showInManager: Boolean? = null

    @SerializedName("children")
    var children: HashMap<String, TagModel>? = null

    var parentName: String? = null

    constructor(name: String) : this() {
        this.name = name
    }

    constructor(source: Parcel) : this() {
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