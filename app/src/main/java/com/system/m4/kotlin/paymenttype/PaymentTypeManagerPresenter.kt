package com.system.m4.kotlin.paymenttype

import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener

/**
 * Created by eferraz on 01/09/17.
 * Presenter
 */
class PaymentTypeManagerPresenter(private val view: PaymentTypeManagerContract.View) : PaymentTypeManagerContract.Presenter {

    private lateinit var mModel: PaymentTypeModel

    override fun init(model: PaymentTypeModel?) {
        mModel = if (model != null) model else PaymentTypeModel()
        view.fillFields(mModel)
    }

    override fun done(name: String) {

        view.showLoading()

        mModel.name = name

        if (mModel.key.isNullOrBlank()) {

            PaymentTypeBusiness().save(mModel, object : PersistenceListener<PaymentTypeModel> {

                override fun onSuccess(model: PaymentTypeModel) {
                    view.returnData(model)
                    view.stopLoading()
                }

                override fun onError(error: String) {
                    view.showError(error)
                    view.stopLoading()
                }
            })

        } else {

            PaymentTypeBusiness().update(mModel, object : PersistenceListener<PaymentTypeModel> {

                override fun onSuccess(model: PaymentTypeModel) {
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