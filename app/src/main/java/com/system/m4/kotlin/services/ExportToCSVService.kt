package com.system.m4.kotlin.services

import android.app.IntentService
import android.content.Context
import android.content.Intent

class ExportToCSVService : IntentService("ExportToCSVService") {

    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val action = intent.action
            if (ACTION_BACKUP == action) {

                ExportToCSVBusiness.findData(intent.getIntExtra(EXTRA_YEAR, 2017),
                        intent.getIntExtra(EXTRA_MONTH, 0))
            }
        }
    }

    companion object {

        private val ACTION_BACKUP = "com.system.m4.kotlin.services.action.BACKUP"
        private val EXTRA_MONTH = "MONTH"
        private val EXTRA_YEAR = "YEAR"

        fun startActionBackup(context: Context, year: Int, month: Int) {
            val intent = Intent(context, ExportToCSVService::class.java)
            intent.action = ACTION_BACKUP
            intent.putExtra(EXTRA_YEAR, year)
            intent.putExtra(EXTRA_MONTH, month)
            context.startService(intent)
        }
    }
}
