package com.system.m4.kotlin.services

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener
import com.system.m4.kotlin.tags.TagBusiness
import com.system.m4.kotlin.tags.TagModel
import com.system.m4.kotlin.transaction.TransactionBusiness
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

object ExportToCSVBusiness {

    fun findData(year: Int, month: Int) {

        var listTransaction: ArrayList<TransactionVO>? = null
        var listTag: List<TagVO>? = null

        TransactionBusiness.findAll(year, month, object : MultResultListener<TransactionVO> {
            override fun onSuccess(list: ArrayList<TransactionVO>) {
                listTransaction = list
                validate(year, month, listTransaction, listTag)
            }

            override fun onError(error: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        TagBusiness.findAll(object : MultResultListener<TagModel> {
            override fun onSuccess(list: ArrayList<TagModel>) {
                listTag = TagBusiness.fromTag(list)
                validate(year, month, listTransaction, listTag)
            }

            override fun onError(error: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun validate(year: Int, month: Int, listTransaction: ArrayList<TransactionVO>?, listTag: List<TagVO>?) {

        if (listTransaction != null && !listTransaction.isEmpty() && listTag != null && !listTag.isEmpty()) {

            for (transaction in listTransaction) {
                TransactionBusiness.fillTransaction(transaction, listTag, null)
            }

            val tagSummaries = TagBusiness.calculateTagSummary(listTransaction)

            val builder = StringBuilder()
            builder.append("Super Category")
            builder.append(",")
            builder.append("Category")
            builder.append(",")
            builder.append("Value")
            builder.append(",")

            for (summary in tagSummaries) {

                builder.append("\r\n")
                builder.append(summary.parentName)
                builder.append(",")
                builder.append(summary.name)
                builder.append(",")
                builder.append("\"").append(String.format("%10.2f", summary.value)).append("\"")
                builder.append(",")
            }

            write(builder, year, month)
        }
    }

    private fun write(builder: StringBuilder, year: Int, month: Int) {

        val baos = ByteArrayOutputStream()
        baos.write(builder.toString().toByteArray())

        val storage = FirebaseStorage.getInstance()

        val format = String.format("%02d", month + 1)
        val storageReference = storage.getReferenceFromUrl("gs://androidm4-f609c.appspot.com").child("backup_${year}_${format}.csv")

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
}