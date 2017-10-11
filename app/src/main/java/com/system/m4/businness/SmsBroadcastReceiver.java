package com.system.m4.businness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.system.m4.kotlin.services.SMSReaderBusiness;
import com.system.m4.kotlin.transaction.TransactionBusiness;
import com.system.m4.views.vos.Transaction;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {

            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);

            if (sms != null) {

                for (Object sm : sms) {

                    String format = intentExtras.getString("format");
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm, format);

                    Transaction transaction = SMSReaderBusiness.Companion.readSMS(context, smsMessage.getMessageBody());
                    if (transaction != null) {
                        new TransactionBusiness().save(transaction, null);
                    }
                }
            }
        }
    }
}