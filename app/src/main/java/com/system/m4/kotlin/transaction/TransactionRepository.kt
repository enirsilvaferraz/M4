package com.system.m4.kotlin.transaction

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.system.m4.BuildConfig
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.infrastructure.utils.KotlinUtils

/**
 * Created by enirs on 30/09/2017.
 * Repository for Transaction
 */
class TransactionRepository(val year: Int, val month: Int) {

    val mFireRef = FirebaseDatabase.getInstance().getReference("${BuildConfig.FLAVOR}/Register/$year/${(month + 1)}/Transaction/")

    fun create(Model: TransactionModel, listener: PersistenceListener<TransactionModel>) {

        val push = mFireRef.push()
        Model.key = push.key

        push.setValue(Model) { databaseError, databaseReference ->
            if (databaseError == null) {
                listener.onSuccess(Model)
            } else {
                listener.onError(databaseError.message)
            }
        }
    }

    fun update(Model: TransactionModel, listener: PersistenceListener<TransactionModel>) {

        mFireRef.child(Model.key).updateChildren(KotlinUtils().modelToMap(Model)) { databaseError, databaseReference ->
            if (databaseError == null) {
                listener.onSuccess(Model)
            } else {
                listener.onError(databaseError.message)
            }
        }
    }

    fun delete(Model: TransactionModel, listener: PersistenceListener<TransactionModel>) {

        mFireRef.child(Model.key).removeValue { databaseError, databaseReference ->
            if (databaseError == null) {
                listener.onSuccess(Model)
            } else {
                listener.onError(databaseError.message)
            }
        }
    }

    fun findAll(listener: MultResultListener<TransactionModel>) {

        mFireRef.orderByChild("key").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                val resultList = arrayListOf<TransactionModel>()
                p0?.children?.mapTo(resultList) {
                    it.getValue(TransactionModel::class.java)
                }
                listener.onSuccess(resultList)
            }

            override fun onCancelled(p0: DatabaseError?) {
                listener.onError(p0?.message!!)
            }
        })
    }
}