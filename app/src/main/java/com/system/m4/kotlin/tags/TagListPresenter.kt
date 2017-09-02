package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Presenter
 */
class TagListPresenter(private val view: TagListContract.View) : TagListContract.Presenter {

    override fun init() {
        findAllTags()
    }

    override fun selectItem(model: TagModel) {
        view.select(model)
    }

    private fun findAllTags() {

        view.showLoading()
        TagBusiness.findAll(object : MultResultListener<TagModel> {

            override fun onSuccess(list: ArrayList<TagModel>) {
                view.loadData(list)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun delete(model: TagModel) {

        view.showLoading()
        TagBusiness.delete(model, object : PersistenceListener<TagModel> {

            override fun onSuccess(model: TagModel) {
                view.removeData(model)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun createModel(model: TagModel) {
        view.addData(model)
    }

    override fun edit(model: TagModel) {
        view.openDialogManager(model)
    }

    override fun addItem() {
        view.openDialogManager(null)
    }
}