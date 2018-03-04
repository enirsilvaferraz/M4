package com.system.m4.kotlin.tags.manager

import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TagManagerModule(val view: TagManagerContract.View) {

    @Singleton
    @Provides
    fun provideRepository(): TagRepository = TagRepository()

    @Singleton
    @Provides
    fun provideBusiness(repository: TagRepository): TagBusiness = TagBusiness(repository)

    @Singleton
    @Provides
    fun providePresenter(business: TagBusiness): TagManagerContract.Presenter = TagManagerPresenter(view, business)
}