package com.system.m4.kotlin.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.system.m4.kotlin.transaction.TransactionBusiness

class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val intentExtras = intent.extras

        if (intentExtras != null) {

            val sms = intentExtras.get(SMS_BUNDLE) as Array<*>

            for (sm in sms) {

                val format = intentExtras.getString(SMS_FORMAT)
                val smsMessage = SmsMessage.createFromPdu(sm as ByteArray, format)

                val transaction = SmsReaderBusiness.readSMS(context, smsMessage.messageBody)
                if (transaction != null) {
                    TransactionBusiness.save(context, transaction, null)
                }
            }
        }
    }

    companion object {

        val SMS_BUNDLE = "pdus"
        val SMS_FORMAT = "format"
    }
}