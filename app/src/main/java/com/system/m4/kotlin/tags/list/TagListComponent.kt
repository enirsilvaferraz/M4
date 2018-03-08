package com.system.m4.kotlin.tags.list

import com.system.m4.kotlin.tags.TagBusiness
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

    companion object {

        fun injectObject(target: TagListDialog) {
            DaggerTagListComponent.builder().tagListModule(TagListComponent.TagListModule(target)).build().inject(target)
        }
    }
}