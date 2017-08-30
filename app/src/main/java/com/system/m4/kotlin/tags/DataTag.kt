package com.system.m4.kotlin.tags

import com.google.gson.annotations.SerializedName

/**
 * Created by enirs on 30/08/2017.
 * Data Tag
 */
data class DataTag(
        @SerializedName("key") var key: String?,
        @SerializedName("name") var name: String
)