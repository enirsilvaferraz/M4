package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class TagBusiness {

    companion object {

        fun save(model: DataTag, listener: PersistenceListener<DataTag>) {
            TagRepository().save(model, listener)
        }

        fun update(model: DataTag, listener: PersistenceListener<DataTag>) {
            TagRepository().update(model, listener)
        }

        fun delete(model: DataTag, listener: PersistenceListener<DataTag>) {
            TagRepository().delete(model, listener)
        }

        fun findAll(listener: MultResultListener<DataTag>) {
            TagRepository().findAll("name", listener)
        }
    }
}