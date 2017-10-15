package com.system.m4.kotlin.group

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.system.m4.BuildConfig
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener

/**
 * Created by enirs on 30/09/2017.
 */
object GroupTransactionRepository {

    val mFireRef = FirebaseDatabase.getInstance().getReference("${BuildConfig.FLAVOR}/GroupTransaction/")

    fun findAll(listener: MultResultListener<GroupTransactionDTO>) {

        mFireRef.orderByChild("key").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                val resultList = arrayListOf<GroupTransactionDTO>()
                p0?.children!!.mapTo(resultList) {
                    it.getValue(GroupTransactionDTO::class.java)!!
                }
                listener.onSuccess(resultList)
            }

            override fun onCancelled(p0: DatabaseError?) {
                listener.onError(p0?.message!!)
            }
        })
    }
}