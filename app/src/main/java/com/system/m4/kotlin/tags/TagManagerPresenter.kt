package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by eferraz on 01/09/17.
 * Presenter
 */
class TagManagerPresenter(private val view: TagManagerContract.View) : TagManagerContract.Presenter {

    private lateinit var mModel: TagModel

    override fun init(model: TagModel?) {
        mModel = if (model != null) model else TagModel()
        loadParent();
    }

    private fun loadParent() {

        view.showLoading()
        TagBusiness.findAllParent(object : MultResultListener<String> {

            override fun onSuccess(list: ArrayList<String>) {
                view.configureFields(list)
                view.fillFields(mModel)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun done(name: String) {

        view.showLoading()

        mModel.name = name

        if (mModel.key.isNullOrBlank()) {

            TagBusiness.create(mModel, object : PersistenceListener<TagModel> {

                override fun onSuccess(model: TagModel) {
                    view.returnData(model)
                    view.stopLoading()
                }

                override fun onError(error: String) {
                    view.showError(error)
                    view.stopLoading()
                }
            })

        } else {

            TagBusiness.update(mModel, object : PersistenceListener<TagModel> {

                override fun onSuccess(model: TagModel) {
                    view.returnData(model)
                    view.stopLoading()
                }

                override fun onError(error: String) {
                    view.showError(error)
                    view.stopLoading()
                }
            })
        }
    }

    override fun cancel() {
        view.returnData(null)
    }
}