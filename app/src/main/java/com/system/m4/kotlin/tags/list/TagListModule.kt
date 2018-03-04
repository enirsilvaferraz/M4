package com.system.m4.kotlin.tags.list

import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TagListModule(val view: TagListContract.View) {

    @Singleton
    @Provides
    fun provideRepository(): TagRepository = TagRepository()

    @Singleton
    @Provides
    fun provideBusiness(repository: TagRepository): TagBusiness = TagBusiness(repository)

    @Singleton
    @Provides
    fun providePresenter(business: TagBusiness): TagListContract.Presenter = TagListPresenter(view, business)
}