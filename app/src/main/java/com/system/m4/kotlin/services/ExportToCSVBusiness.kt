package com.system.m4.kotlin.services

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.kotlin.transaction.TransactionBusiness
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object ExportToCSVBusiness {

    fun findData() {

        TagBusiness.findAll(object : MultResultListener<TagModel> {

            override fun onSuccess(list: ArrayList<TagModel>) {

                val tags = TagBusiness.fromTag(list)
                val transactionsList = arrayOfNulls<ArrayList<TransactionVO>>(12)
                val year = Calendar.getInstance().get(Calendar.YEAR)

                for (month in 0..11) {

                    TransactionBusiness.findAll(year, month, object : MultResultListener<TransactionVO> {

                        override fun onSuccess(list: ArrayList<TransactionVO>) {
                            transactionsList[month] = TransactionBusiness.fillTransaction(list, tags, null)
                            configureList(transactionsList, tags, year)
                        }

                        override fun onError(error: String) {}
                    })
                }
            }

            override fun onError(error: String) {}
        })
    }

    private fun configureList(transactionsList: Array<ArrayList<TransactionVO>?>, tags: ArrayList<TagVO>, year: Int) {

        for (arrayList in transactionsList) {
            if (arrayList == null) {
                return
            }
        }

        val summaries = ArrayList<SummaryExport>()
        for (tag in tags) {

            if (tag.parentName.isNullOrBlank()) {
                continue
            }

            val summary = SummaryExport(tag.parentName, tag.name, arrayListOf<SummaryAmount>(
                    SummaryAmount(0, 0.0),
                    SummaryAmount(1, 0.0),
                    SummaryAmount(2, 0.0),
                    SummaryAmount(3, 0.0),
                    SummaryAmount(4, 0.0),
                    SummaryAmount(5, 0.0),
                    SummaryAmount(6, 0.0),
                    SummaryAmount(7, 0.0),
                    SummaryAmount(8, 0.0),
                    SummaryAmount(9, 0.0),
                    SummaryAmount(10, 0.0),
                    SummaryAmount(11, 0.0)
            ))

            for ((index, arrayList) in transactionsList.withIndex()) {
                if (arrayList != null) {
                    for (transaction in arrayList) {
                        if (tag.key.equals(transaction.tag.key)) {
                            summary.amounts[index].amount += transaction.price
                        }
                    }
                }
            }

            summaries.add(summary)
        }

        createFile(summaries, year)

    }

    private fun createFile(summaries: ArrayList<SummaryExport>, year: Int) {

        val builder = StringBuilder()
        builder.append("Super Category")
        builder.append(",")
        builder.append("Category")
        builder.append(",")

        for (index in 0..11) {
            builder.append(JavaUtils.DateUtil.getMonth(index))
            builder.append(",")
        }

        for (summary in summaries) {

            builder.append("\r\n")
            builder.append(summary.parentName)
            builder.append(",")
            builder.append(summary.name)
            builder.append(",")

            for (amount in summary.amounts) {
                builder.append("\"").append(String.format("%10.2f", amount.amount)).append("\"")
                builder.append(",")
            }
        }

        write(builder, year)
    }

    private fun write(builder: StringBuilder, year: Int) {

        val baos = ByteArrayOutputStream()
        baos.write(builder.toString().toByteArray())

        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.getReferenceFromUrl("gs://androidm4-f609c.appspot.com").child("backup_${year}.csv")

        val uploadTask = storageReference.putBytes(baos.toByteArray())
        uploadTask.addOnFailureListener(object : OnFailureListener {
            override fun onFailure(p0: Exception) {
                p0.cause
            }
        })
        uploadTask.addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
            override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                p0.isComplete
            }
        })
    }

    data class SummaryExport(val parentName: String, val name: String, val amounts: ArrayList<SummaryAmount>)
    data class SummaryAmount(val month: Int, var amount: Double)
}