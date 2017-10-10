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

    private val PATH_REF = "${BuildConfig.FLAVOR}/Tag"

    private fun getFireRef(parentKey: String? = null): DatabaseReference {
        val path = if (parentKey.isNullOrBlank()) PATH_REF else "$PATH_REF/${parentKey}/children"
        return FirebaseDatabase.getInstance().getReference(path)
    }

    fun create(model: TagModel, listener: PersistenceListener<TagModel>) {

        val push = getFireRef(model.parentKey).push()
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

    fun update(model: TagModel, listener: PersistenceListener<TagModel>) {

        getFireRef(model.parentKey).child(model.key).updateChildren(KotlinUtils().modelToMap(model), object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, p1: DatabaseReference?) {
                if (error == null) {
                    listener.onSuccess(model)
                } else {
                    listener.onError(error.message)
                }
            }
        })
    }

    fun delete(model: TagModel, listener: PersistenceListener<TagModel>) {

        getFireRef(model.parentKey).child(model.key).removeValue(object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, p1: DatabaseReference?) {
                if (error == null) {
                    listener.onSuccess(model)
                } else {
                    listener.onError(error.message)
                }
            }
        })
    }

    fun findAll(orderBy: String = "key", listener: MultResultListener<TagModel>) {

        getFireRef().orderByChild(orderBy).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                val resultList = arrayListOf<TagModel>()
                p0?.children!!.mapTo(resultList) {

                    //val valor =
                    it.getValue(TagModel::class.java)!!

//                    if (it.child("children").exists()) {
//                        valor.child = arrayListOf()
//                        it.child("children").children.mapTo(valor.child!!) {
//                            it.getValue(TagModel::class.java)
//                        }
//                    }
//
//                    valor
                }
                listener.onSuccess(resultList)
            }

            override fun onCancelled(p0: DatabaseError?) {
                listener.onError(p0?.message!!)
            }
        })
    }
}