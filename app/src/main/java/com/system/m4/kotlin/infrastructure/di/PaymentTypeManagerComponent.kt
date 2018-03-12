package com.system.m4.kotlin.infrastructure.di

import com.system.m4.kotlin.contracts.PaymentTypeManagerContract
import com.system.m4.kotlin.model.business.PaymentTypeBusiness
import com.system.m4.kotlin.presenters.PaymentTypeManagerPresenter
import com.system.m4.kotlin.view.fragments.PaymentTypeManagerDialog
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
}

