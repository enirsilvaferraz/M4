package com.system.m4.kotlin.infrastructure.di

import com.system.m4.kotlin.view.fragments.PaymentTypeListDialog
import com.system.m4.kotlin.view.fragments.PaymentTypeManagerDialog
import com.system.m4.kotlin.view.fragments.TagListDialog
import com.system.m4.kotlin.view.fragments.TagManagerDialog

object DaggerFactory {

    fun injectObject(target: PaymentTypeListDialog) {
        DaggerPaymentTypeListComponent.builder().paymentTypeModule(PaymentTypeListComponent.PaymentTypeModule(target)).build().inject(target)
    }

    fun injectObject(target: PaymentTypeManagerDialog) {
        DaggerPaymentTypeManagerComponent.builder().paymentTypeModule(PaymentTypeManagerComponent.PaymentTypeModule(target)).build().inject(target)
    }

    fun injectObject(target: TagListDialog) {
        DaggerTagListComponent.builder().tagListModule(TagListComponent.TagListModule(target)).build().inject(target)
    }

    fun injectObject(target: TagManagerDialog) {
        DaggerTagManagerComponent.builder().tagManagerModule(TagManagerComponent.TagManagerModule(target)).build().inject(target)
    }
}