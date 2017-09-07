package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by eferraz on 01/09/17.
 * Presenter
 */
class TagManagerPresenter(private val view: TagManagerContract.View) : TagManagerContract.Presenter {

    private lateinit var mModel: TagModel
    private var mParent: TagModel? = null


    override fun init(model: TagModel?) {
        mModel = if (model != null) model else TagModel()
        loadParent()
    }

    private fun loadParent() {

        view.showLoading()
        TagBusiness.findAllParents(object : MultResultListener<TagModel> {

            override fun onSuccess(list: ArrayList<TagModel>) {
                list.add(0, TagModel("--"))
                view.fillFields(mModel, list)
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

            TagBusiness.create(model = mModel, parent = mParent, listener = object : PersistenceListener<TagModel> {

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

            TagBusiness.update(child = mModel, parent = mParent, listener = object : PersistenceListener<TagModel> {

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

    override fun configureParent(selected: TagModel) {
        mParent = if (!selected.key.isNullOrBlank()) selected else null
    }
}