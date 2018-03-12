package com.system.m4.kotlin.infrastructure.di

import com.system.m4.kotlin.contracts.PaymentTypeListContract
import com.system.m4.kotlin.model.business.PaymentTypeBusiness
import com.system.m4.kotlin.presenters.PaymentTypeListPresenter
import com.system.m4.kotlin.view.fragments.PaymentTypeListDialog
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
}

