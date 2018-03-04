package com.system.m4.kotlin.tags.list

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TagListModule::class])
interface TagListComponent {
    fun inject(target: TagListDialog)
}