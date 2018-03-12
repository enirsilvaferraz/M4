package com.system.m4.kotlin.infrastructure.di

import com.system.m4.kotlin.contracts.TagListContract
import com.system.m4.kotlin.model.business.TagBusiness
import com.system.m4.kotlin.presenters.TagListPresenter
import com.system.m4.kotlin.view.fragments.TagListDialog
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [TagListComponent.TagListModule::class])
interface TagListComponent {

    fun inject(target: TagListDialog)

    @Module
    class TagListModule(val view: TagListContract.View) {

        @Singleton
        @Provides
        fun providePresenter(business: TagBusiness): TagListContract.Presenter = TagListPresenter(view, business)
    }
}