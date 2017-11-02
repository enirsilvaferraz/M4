package com.system.m4.kotlin.tags

import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.views.vos.TagSummaryVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO

/**
 * Created by enirs on 30/08/2017.
 * Business
 */
class TagBusiness {

    companion object {

        fun create(model: TagModel, parent: TagModel? = null, listener: PersistenceListener<TagModel>) {
            model.parentKey = parent?.key
            TagRepository().create(model, listener)
        }

        fun update(child: TagModel, parent: TagModel? = null, listener: PersistenceListener<TagModel>) {

            // Add parent
            if (child.parentKey.isNullOrBlank() && parent != null) {

                child.parentKey = parent.key
                TagRepository().update(child, object : PersistenceListener<TagModel> {

                    override fun onSuccess(model: TagModel) {
                        child.parentKey = null
                        TagRepository().delete(child, listener)
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            // Remove parent
            else if (!child.parentKey.isNullOrBlank() && parent == null) {

                TagRepository().delete(child, object : PersistenceListener<TagModel> {

                    override fun onSuccess(model: TagModel) {
                        model.parentKey = null
                        TagRepository().update(model, listener)
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            // Move to another parent
            else if ((!child.parentKey.isNullOrBlank() && parent != null) && !child.parentKey.equals(parent.key)) {

                TagRepository().delete(child, object : PersistenceListener<TagModel> {

                    override fun onSuccess(model: TagModel) {
                        model.parentKey = parent.key
                        TagRepository().update(model, listener)
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            // Do not have a parent or the parent is the same. Just update
            else if ((child.parentKey.isNullOrBlank() && parent == null) || child.parentKey.equals(parent?.key)) {
                TagRepository().update(child, listener)
            }
        }

        fun delete(model: TagModel, listener: PersistenceListener<TagModel>) {
            TagRepository().delete(model, listener)
        }

        fun findAllParents(listener: MultResultListener<TagModel>) {
            TagRepository().findAll("name", listener)
        }

        fun findAll(listener: MultResultListener<TagModel>) {

            findAllParents(object : MultResultListener<TagModel> {

                override fun onSuccess(list: ArrayList<TagModel>) {
                    listener.onSuccess(configureList(list))
                }

                override fun onError(error: String) {
                    listener.onError(error)
                }
            })
        }

        private fun configureList(list: ArrayList<TagModel>): ArrayList<TagModel> {
            val array = arrayListOf<TagModel>()
            for (model: TagModel in list) {
                array.add(model)
                if (model.children != null) {
                    for (child in model.children!!.values) {
                        child.parentName = model.name
                    }
                    val elements = model.children!!.values.sortedWith(compareBy({ it.parentName }, { it.name }))
                    array.addAll(elements)
                }
            }
            return array
        }

        fun calculateTagSummary(transactions: ArrayList<TransactionVO>): List<TagSummaryVO> {

            val itens = arrayListOf<TagSummaryVO>()

            for (transaction in transactions) {

                if (transaction.key == null || !transaction.isApproved) {
                    continue
                }

                val summaryVO = TagSummaryVO(transaction.tag.key, transaction.tag.parentName, transaction.tag.name, transaction.price)

                if (itens.contains(summaryVO)) {
                    val item = itens.get(itens.indexOf(summaryVO))
                    item.value = item.value!! + transaction.price!!.toFloat()
                    item.transactions.add(transaction)
                } else {
                    summaryVO.transactions = arrayListOf<TransactionVO>()
                    summaryVO.transactions.add(transaction)
                    itens.add(summaryVO)
                }
            }

            var sortedWith = itens.sortedWith(compareBy({ it.parentName }, { it.name }))
            return sortedWith
        }

        fun calculateTagSummaryToExport(transactions: ArrayList<TransactionVO>, tags: ArrayList<TagVO>): List<TagSummaryVO> {

            val itens = arrayListOf<TagSummaryVO>()

            for (tag in tags) {

                if (tag.parentName.isNullOrBlank()) {
                    continue
                }

                val summary = TagSummaryVO(tag.key, tag.parentName, tag.name, 0.0)

                for (transaction in transactions) {
                    if (transaction.tag.key.equals(tag.key)) {
                        summary.value += transaction.price
                    }
                }

                itens.add(summary)
            }

            return itens
        }

        fun fromTag(list: List<TagModel>): ArrayList<TagVO> {
            val listVO = ArrayList<TagVO>()
            for (model in list) {
                listVO.add(fromTag(model))
            }
            return listVO
        }

        fun fromTag(mVO: TagVO): TagModel {
            val dto = TagModel()
            dto.key = mVO.key
            dto.name = mVO.name
            dto.parentName = mVO.parentName
            return dto
        }

        fun fromTag(dto: TagModel): TagVO {
            val vo = TagVO()
            vo.key = dto.key
            vo.name = dto.name
            vo.parentName = dto.parentName
            return vo
        }
    }
}