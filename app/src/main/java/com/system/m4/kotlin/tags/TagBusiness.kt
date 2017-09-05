package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class TagBusiness {

    companion object {

        fun save(model: TagModel, listener: PersistenceListener<TagModel>) {
            TagRepository().save(model, listener)
        }

        fun update(model: TagModel, listener: PersistenceListener<TagModel>) {
            TagRepository().update(model, listener)
        }

        fun delete(model: TagModel, listener: PersistenceListener<TagModel>) {
            TagRepository().delete(model, listener)
        }

        fun findAll(listener: MultResultListener<TagModel>) {
            TagRepository().findAll("name", listener)
        }

        fun findAllParent(listener: MultResultListener<String>) {

            TagRepository().findAll("name", object : MultResultListener<TagModel>{

                override fun onSuccess(list: ArrayList<TagModel>) {
                    val arrayList: ArrayList<String> = arrayListOf()
                    list.mapTo(arrayList) {
                        it.name!!
                    }
                    listener.onSuccess(list = arrayList)
                }

                override fun onError(error: String) {
                    listener.onError(error)
                }
            })
        }
    }
}