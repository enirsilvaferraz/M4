package com.system.m4.kotlin.paymenttype

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [PaymentTypeComponent.PaymentTypeModule::class])
interface PaymentTypeComponent {

    fun inject(target: PaymentTypeListDialog)

    @Module
    class PaymentTypeModule(val view: PaymentTypeListContract.View) {

        @Singleton
        @Provides
        fun provideRepository(): PaymentTypeRepository = PaymentTypeRepository()

        @Singleton
        @Provides
        fun provideBusiness(repository: PaymentTypeRepository): PaymentTypeBusiness = PaymentTypeBusiness(repository)

        @Singleton
        @Provides
        fun providePresenter(business: PaymentTypeBusiness): PaymentTypeListContract.Presenter = PaymentTypeListPresenter(view, business)
    }

    companion object {

        fun injectObject(target: PaymentTypeListDialog) {
            DaggerPaymentTypeComponent.builder().paymentTypeModule(PaymentTypeModule(target)).build().inject(target)
        }
    }
}

