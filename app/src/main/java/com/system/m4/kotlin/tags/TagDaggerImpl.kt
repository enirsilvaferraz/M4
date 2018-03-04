package com.system.m4.kotlin.tags

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [TagModule::class])
interface TagComponent {
    fun inject(target: TagListDialog)
}

@Module
class TagModule(val view: TagListContract.View) {

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