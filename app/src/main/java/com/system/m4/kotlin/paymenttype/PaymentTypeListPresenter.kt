package com.system.m4.kotlin.paymenttype

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Presenter
 */
class PaymentTypeListPresenter(private val view: PaymentTypeListContract.View, val business: PaymentTypeBusiness) : PaymentTypeListContract.Presenter {

    override fun onInit() {

        view.showLoading()
        business.findAll(object : MultResultListener<PaymentTypeModel> {

            override fun onSuccess(list: ArrayList<PaymentTypeModel>) {
                view.load(list)
                view.stopLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.stopLoading()
            }
        })
    }

    override fun select(model: PaymentTypeModel) {
        view.select(model)
    }

    override fun create() {
        view.openManager(null)
    }

    override fun edit(model: PaymentTypeModel) {
        view.openManager(model)
    }

    override fun delete(model: PaymentTypeModel) {

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
    }
}