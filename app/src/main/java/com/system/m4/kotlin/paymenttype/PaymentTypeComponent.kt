package com.system.m4.kotlin.paymenttype

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PaymentTypeModule::class])
interface PaymentTypeComponent {
    fun inject(target: PaymentTypeListDialog)
}