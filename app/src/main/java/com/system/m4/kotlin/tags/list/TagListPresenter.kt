package com.system.m4.kotlin.tags.list

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagModel
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Presenter
 */
class TagListPresenter(private val view: TagListContract.View, private val business: TagBusiness) : TagListContract.Presenter {

    override fun init() {

        view.showLoading()
        business.findAll(object : MultResultListener<TagModel> {

            override fun onSuccess(list: ArrayList<TagModel>) {
                view.loadTags(list)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun onSelect(model: TagModel) {
        view.select(model)
    }

    override fun create() {
        view.openManager(null)
    }

    override fun onEdit(model: TagModel) {
        view.openManager(model)
    }

    override fun onDelete(model: TagModel) {

        view.showLoading()
        business.delete(model, object : PersistenceListener<TagModel> {

            override fun onSuccess(model: TagModel) {
                view.remove(model)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun addList() {
        init()
    }
}
