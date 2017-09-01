package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by eferraz on 01/09/17.
 */
class TagManagerPresenter(private val view: TagManagerContract.View) : TagManagerContract.Presenter {

    override fun done(name: String) {
        view.showLoading()

        TagBusiness.save(TagModel(name), object : PersistenceListener<TagModel> {
            override fun onSuccess(model: TagModel) {
                view.stopLoading()
                view.closeManager(model)
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun cancel() {
        view.closeManager(null)
    }
}