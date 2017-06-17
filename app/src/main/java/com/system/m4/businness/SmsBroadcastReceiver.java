package com.system.m4.businness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.system.m4.R;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

import java.util.Calendar;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    public static final Integer ITAU_SMS_NUMBER = 25001;

    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {

            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            if (sms != null) {
                for (Object sm : sms) {
                    String format = intentExtras.getString("format");
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm, format);

                    //if (ITAU_SMS_NUMBER.equals(Integer.valueOf(smsMessage.getOriginatingAddress()))) {
                    new Reader().readSMS(context, smsMessage.getMessageBody());
                    //}
                }
            }
        }
    }

    private class Reader {

        static final String STR_LOCAL = "Local:";
        static final String STR_RS = "R$";
        static final String STR_COMPRA_APROVADA = "COMPRA APROVADA";
        static final String TITLE_ITAU_DEBITO = "ITAU DEBITO";

        /**
         * Template : ITAU DEBITO: Cartao final 0934 COMPRA APROVADA 11/06 08:11:39 R$ 50,00 Local: POSTO ODE. Consulte também pelo celular www.itau.com.br
         */
        private void readSMS(Context context, String message) {

            if (message.startsWith(TITLE_ITAU_DEBITO)) {

                Transaction vo = new Transaction();
                vo.setPaymentType(new PaymentTypeVO());
                vo.getPaymentType().setKey(context.getResources().getString(R.string.paymenttype_debito_key));

                vo.setTag(new TagVO());
                vo.getTag().setKey(context.getResources().getString(R.string.tag_debito_key));

                int localIndexStart = message.indexOf(STR_LOCAL);
                int localIndexEnd = message.indexOf(".", localIndexStart);
                vo.setContent(message.substring(localIndexStart + STR_LOCAL.length(), localIndexEnd).trim());

                int currencyIndex = message.indexOf(STR_RS);
                String price = message.substring(currencyIndex + STR_RS.length(), localIndexStart).trim().replace(",", ".");
                vo.setPrice(Double.valueOf(price));

                int dateIndex = message.indexOf(STR_COMPRA_APROVADA);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                String date = message.substring(dateIndex + STR_COMPRA_APROVADA.length(), currencyIndex).trim().replace(" ", "/" + year + " ");
                vo.setPaymentDate(JavaUtils.DateUtil.parse(date, JavaUtils.DateUtil.DD_MM_YYYY_HH_MM_SS));

                TransactionBusinness.save(vo, null);
            }

            //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}