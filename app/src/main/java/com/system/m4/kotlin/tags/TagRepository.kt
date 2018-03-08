package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.GenericRepository
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by eferraz on 30/08/2017.
 * Repository para Tag
 */
class TagRepository @Inject constructor() : GenericRepository<TagModel>("Tag") {
    override fun kClass(): KClass<TagModel> = TagModel::class
}