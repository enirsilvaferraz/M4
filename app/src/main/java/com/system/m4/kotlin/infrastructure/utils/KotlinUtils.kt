package com.system.m4.kotlin.infrastructure.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by enirs on 30/08/2017.
 */
class KotlinUtils {

    fun modelToMap(model: Any): MutableMap<String, Any>? {
        val gson: Gson = GsonBuilder().create()
        val json: String = gson.toJson(model)
        return gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
    }
}