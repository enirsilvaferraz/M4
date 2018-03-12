package com.system.m4.kotlin.model.services

import android.app.IntentService
import android.content.Context
import android.content.Intent

class ExportToCSVService : IntentService("ExportToCSVService") {

    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val action = intent.action
            if (ACTION_BACKUP == action) {
                ExportToCSVBusiness.findData()
            }
        }
    }

    companion object {

        private val ACTION_BACKUP = "com.system.m4.kotlin.model.services.action.BACKUP"
        private val EXTRA_MONTH = "MONTH"
        private val EXTRA_YEAR = "YEAR"

        fun startActionBackup(context: Context) {
            val intent = Intent(context, ExportToCSVService::class.java)
            intent.action = ACTION_BACKUP
            context.startService(intent)
        }
    }
}
