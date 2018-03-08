package com.system.m4.kotlin.paymenttype.manager

import com.system.m4.kotlin.paymenttype.PaymentTypeBusiness
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [PaymentTypeManagerComponent.PaymentTypeModule::class])
interface PaymentTypeManagerComponent {

    fun inject(target: PaymentTypeManagerDialog)

    @Module
    class PaymentTypeModule(val view: PaymentTypeManagerContract.View) {

        @Singleton
        @Provides
        fun providePresenter(business: PaymentTypeBusiness): PaymentTypeManagerContract.Presenter = PaymentTypeManagerPresenter(view, business)
    }

    companion object {

        fun injectObject(target: PaymentTypeManagerDialog) {
            DaggerPaymentTypeManagerComponent.builder().paymentTypeModule(PaymentTypeModule(target)).build().inject(target)
        }
    }
}

