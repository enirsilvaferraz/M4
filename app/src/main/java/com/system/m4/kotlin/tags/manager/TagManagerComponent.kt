package com.system.m4.kotlin.tags.manager

import com.system.m4.kotlin.tags.TagBusiness
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

    companion object {

        fun injectObject(target: TagManagerDialog) {
            DaggerTagManagerComponent.builder().tagManagerModule(TagManagerComponent.TagManagerModule(target)).build().inject(target)
        }
    }
}