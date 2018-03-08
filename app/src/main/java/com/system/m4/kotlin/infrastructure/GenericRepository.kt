package com.system.m4.kotlin.infrastructure

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.system.m4.BuildConfig
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.infrastructure.utils.KotlinUtils
import kotlin.reflect.KClass

/**
 * Created by eferraz on 30/08/2017.
 * Repository para Tag
 */
abstract class GenericRepository<T : GenericRepository.Model>(val database: String) {

    val mFireRef = FirebaseDatabase.getInstance().getReference("${BuildConfig.FLAVOR}/${database}")

    fun save(model: T, listener: PersistenceListener<T>) {
        val push = mFireRef.push()
        model.key = push.key
        push.setValue(model) { error, _ ->
            if (error == null) listener.onSuccess(model)
            else listener.onError(error.message)
        }
    }

    fun update(model: T, listener: PersistenceListener<T>) {
        mFireRef.child(model.key).updateChildren(KotlinUtils().modelToMap(model)) { error, _ ->
            if (error == null) listener.onSuccess(model)
            else listener.onError(error.message)
        }
    }

    fun delete(model: T, listener: PersistenceListener<T>) {
        mFireRef.child(model.key).removeValue { error, _ ->
            if (error == null) listener.onSuccess(model)
            else listener.onError(error.message)
        }
    }

    fun findAll(orderBy: String = "key", listener: MultResultListener<T>) {
        mFireRef.orderByChild(orderBy).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                val resultList = arrayListOf<T>()
                p0?.children!!.mapTo(resultList) {
                    it.getValue(kClass().java)!!
                }
                listener.onSuccess(resultList)
            }

            override fun onCancelled(p0: DatabaseError?) {
                listener.onError(p0?.message!!)
            }
        })
    }

    abstract fun kClass(): KClass<T>

    interface Model {
        var key: String?
    }
}