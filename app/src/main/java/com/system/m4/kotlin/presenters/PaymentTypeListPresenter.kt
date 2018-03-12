package com.system.m4.kotlin.presenters

import com.system.m4.kotlin.contracts.PaymentTypeListContract
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.model.business.PaymentTypeBusiness
import com.system.m4.kotlin.model.entity.PaymentTypeModel
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Presenter
 */
class PaymentTypeListPresenter(private val view: PaymentTypeListContract.View, private val business: PaymentTypeBusiness) : PaymentTypeListContract.Presenter {

    override fun onInit() {

        view.showLoading()
        business.findAll(object : MultResultListener<PaymentTypeModel> {

            override fun onSuccess(list: ArrayList<PaymentTypeModel>) {
                view.loadList(list)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun onSelectClicked(model: PaymentTypeModel): Boolean {
        view.selectItem(model)
        view.dismiss()
        return true
    }

    override fun onEditClicked(model: PaymentTypeModel): Boolean {
        view.openManager(model)
        return true
    }

    override fun onDeleteClicked(model: PaymentTypeModel): Boolean {
        view.showLoading()
        business.delete(model, object : PersistenceListener<PaymentTypeModel> {

            override fun onSuccess(model: PaymentTypeModel) {
                view.remove(model)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
        return true
    }

    override fun onAddNewClicked() {
        view.openManager(null)
    }

    override fun onComplete(model: PaymentTypeModel) {
        view.addOrUpdateItem(model)
    }
}