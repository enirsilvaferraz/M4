package com.system.m4.kotlin.model.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.system.m4.kotlin.model.business.TransactionBusiness
import com.system.m4.labs.vos.TransactionVO

class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val intentExtras = intent.extras

        if (intentExtras != null) {

            val sms = intentExtras.get(SMS_BUNDLE) as Array<*>

            for (sm in sms) {

                val format = intentExtras.getString(SMS_FORMAT)
                val smsMessage = SmsMessage.createFromPdu(sm as ByteArray, format)

                val transaction: TransactionVO? = SmsReaderBusiness.readSMS(context, smsMessage.messageBody)
                if (transaction != null) {
                    TransactionBusiness().save(transaction, null)
                }
            }
        }
    }

    companion object {

        val SMS_BUNDLE = "pdus"
        val SMS_FORMAT = "format"
    }
}