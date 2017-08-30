package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Presenter
 */
class TagPresenter(private val view: TagContract.View) {

    fun init() {
        findAllTags()
    }

    private fun findAllTags() {

        view.showLoading()
        TagBusiness.findAll(object : MultResultListener<DataTag> {

            override fun onSuccess(list: ArrayList<DataTag>) {
                view.loadData(list)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    fun save(model: DataTag) {

        if (model.key.isNullOrBlank()) {

            view.showLoading()
            TagBusiness.save(model, object : PersistenceListener<DataTag> {

                override fun onSuccess(model: DataTag) {
                    view.addData(model)
                }

                override fun onError(error: String) {
                    view.showError(error)
                    view.stopLoading()
                }
            })

        } else {

            view.showLoading()
            TagBusiness.update(model, object : PersistenceListener<DataTag> {

                override fun onSuccess(model: DataTag) {
                    view.updateData(model)
                }

                override fun onError(error: String) {
                    view.showError(error)
                    view.stopLoading()
                }
            })
        }
    }


    fun delete(model: DataTag) {

        view.showLoading()
        TagBusiness.delete(model, object : PersistenceListener<DataTag> {

            override fun onSuccess(model: DataTag) {
                view.removeData(model)
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }
}