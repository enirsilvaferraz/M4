package com.system.m4.kotlin.infrastructure.di

import com.system.m4.kotlin.contracts.TagManagerContract
import com.system.m4.kotlin.model.business.TagBusiness
import com.system.m4.kotlin.presenters.TagManagerPresenter
import com.system.m4.kotlin.view.fragments.TagManagerDialog
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [TagManagerComponent.TagManagerModule::class])
interface TagManagerComponent {

    fun inject(target: TagManagerDialog)

    @Module
    class TagManagerModule(val view: TagManagerContract.View) {

        @Singleton
        @Provides
        fun providePresenter(business: TagBusiness): TagManagerContract.Presenter = TagManagerPresenter(view, business)
    }
}