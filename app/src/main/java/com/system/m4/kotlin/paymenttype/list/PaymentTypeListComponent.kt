package com.system.m4.kotlin.paymenttype.list

import com.system.m4.kotlin.paymenttype.PaymentTypeBusiness
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [PaymentTypeListComponent.PaymentTypeModule::class])
interface PaymentTypeListComponent {

    fun inject(target: PaymentTypeListDialog)

    @Module
    class PaymentTypeModule(val view: PaymentTypeListContract.View) {

        @Singleton
        @Provides
        fun providePresenter(business: PaymentTypeBusiness): PaymentTypeListContract.Presenter = PaymentTypeListPresenter(view, business)
    }

    companion object {

        fun injectObject(target: PaymentTypeListDialog) {
            DaggerPaymentTypeListComponent.builder().paymentTypeModule(PaymentTypeModule(target)).build().inject(target)
        }
    }
}

