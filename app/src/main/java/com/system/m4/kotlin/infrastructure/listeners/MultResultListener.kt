package com.system.m4.kotlin.infrastructure.listeners

/**
 * Created by eferraz on 30/08/2017.
 * Listener para retorno de servicos
 */
interface MultResultListener<T> {
    fun onSuccess(list: ArrayList<T>)
    fun onError(error: String)
}