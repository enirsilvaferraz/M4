package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class TagBusiness {

    companion object {

        fun create(model: TagModel, parent: TagModel? = null, listener: PersistenceListener<TagModel>) {
            model.parentKey = parent?.key
            TagRepository().create(model, listener)
        }

        fun update(child: TagModel, parent: TagModel? = null, listener: PersistenceListener<TagModel>) {

            // Add parent
            if (child.parentKey.isNullOrBlank() && parent != null) {

                child.parentKey = parent.key
                TagRepository().update(child, object : PersistenceListener<TagModel> {

                    override fun onSuccess(model: TagModel) {
                        child.parentKey = null
                        TagRepository().delete(child, listener)
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            // Remove parent
            else if (!child.parentKey.isNullOrBlank() && parent == null) {

                TagRepository().delete(child, object : PersistenceListener<TagModel> {

                    override fun onSuccess(model: TagModel) {
                        model.parentKey = null
                        TagRepository().update(model, listener)
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            // Move to another parent
            else if ((!child.parentKey.isNullOrBlank() && parent != null) && !child.parentKey.equals(parent.key)) {

                TagRepository().delete(child, object : PersistenceListener<TagModel> {

                    override fun onSuccess(model: TagModel) {
                        model.parentKey = parent.key
                        TagRepository().update(model, listener)
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            // Do not have a parent or the parent is the same. Just update
            else if ((child.parentKey.isNullOrBlank() && parent == null) || child.parentKey.equals(parent?.key)) {
                TagRepository().update(child, listener)
            }
        }

        fun delete(model: TagModel, listener: PersistenceListener<TagModel>) {
            TagRepository().delete(model, listener)
        }

        fun findAll(listener: MultResultListener<TagModel>) {
            TagRepository().findAll("name", listener)
        }

        fun findAllForManager(listener: MultResultListener<TagModel>) {

            findAll(object : MultResultListener<TagModel> {

                override fun onSuccess(list: ArrayList<TagModel>) {
                    val array = arrayListOf<TagModel>()
                    for (it: TagModel in list) {
                        if (it.showInManager == null || it.showInManager!!) {
                            array.add(it)
                        }
                    }
                    listener.onSuccess(array)
                }

                override fun onError(error: String) {
                    listener.onError(error)
                }
            })
        }
    }
}