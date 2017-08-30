package com.system.m4.kotlin.tags

import com.google.firebase.database.*
import com.system.m4.BuildConfig
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.infrastructure.utils.KotlinUtils

/**
 * Created by eferraz on 30/08/2017.
 * Repository para Tag
 */
class TagRepository {

    val mFireRef = FirebaseDatabase.getInstance().getReference(" ${BuildConfig.FLAVOR}/${DataTag::class.simpleName}")

    fun save(model: DataTag, listener: PersistenceListener<DataTag>) {

        val push = mFireRef.push()
        model.key = push.key
        push.setValue(model, object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, p1: DatabaseReference?) {
                if (error == null) {
                    listener.onSuccess(model)
                } else {
                    listener.onError(error.message)
                }
            }
        })
    }

    fun update(model: DataTag, listener: PersistenceListener<DataTag>) {

        mFireRef.updateChildren(KotlinUtils().modelToMap(model), object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, p1: DatabaseReference?) {
                if (error == null) {
                    listener.onSuccess(model)
                } else {
                    listener.onError(error.message)
                }
            }
        })
    }

    fun delete(model: DataTag, listener: PersistenceListener<DataTag>) {

        mFireRef.child(model.key).removeValue(object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, p1: DatabaseReference?) {
                if (error == null) {
                    listener.onSuccess(model)
                } else {
                    listener.onError(error.message)
                }
            }
        })
    }

    fun findAll(orderBy: String = "key", listener: MultResultListener<DataTag>) {

        mFireRef.orderByChild(orderBy).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                val resultList = arrayListOf<DataTag>()
                p0?.children?.mapTo(resultList) {
                    it.getValue(DataTag::class.java)
                }
                listener.onSuccess(resultList)
            }

            override fun onCancelled(p0: DatabaseError?) {
                listener.onError(p0?.message!!)
            }
        })
    }
}