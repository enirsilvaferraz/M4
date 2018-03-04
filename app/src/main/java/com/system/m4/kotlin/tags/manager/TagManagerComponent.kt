package com.system.m4.kotlin.tags.manager

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TagManagerModule::class])
interface TagManagerComponent {
    fun inject(target: TagManagerDialog)
}