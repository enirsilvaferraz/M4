package com.system.m4.kotlin.tags.manager

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagModel

/**
 * Created by eferraz on 01/09/17.
 * Presenter
 */
class TagManagerPresenter(private val view: TagManagerContract.View, private val business: TagBusiness) : TagManagerContract.Presenter {

    private lateinit var mModel: TagModel
    private var mParent: TagModel? = null


    override fun init(model: TagModel?) {
        mModel = if (model != null) model else TagModel()
        loadParent()
    }

    private fun loadParent() {

        view.showLoading()
        business.findAllParents(object : MultResultListener<TagModel> {

            override fun onSuccess(list: ArrayList<TagModel>) {

                list.add(0, TagModel("--"))

                val enable = mModel.children == null || mModel.children!!.isEmpty()
                enable.let { view.fillFieldParent(model = mModel, list = list) }
                view.enableParentSelection(enable)
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

            business.create(model = mModel, parent = mParent, listener = object : PersistenceListener<TagModel> {

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

            business.update(child = mModel, parent = mParent, listener = object : PersistenceListener<TagModel> {

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