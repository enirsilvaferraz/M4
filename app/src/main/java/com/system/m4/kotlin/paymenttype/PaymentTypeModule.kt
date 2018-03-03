package com.system.m4.kotlin.paymenttype

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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