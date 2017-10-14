package com.system.m4.kotlin.services

import android.content.Context
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.util.*

/**
 * Created by enirs on 11/10/2017.
 * Business para SMS Reader
 */
class SmsReaderBusiness {

    companion object {

        internal val SMS_TITLE_DEBIT = "ITAU DEBITO"

        internal val DEBIT_STR_LOCAL = "Local:"
        internal val DEBIT_STR_RS = "R$"
        internal val DEBIT_STR_COMPRA_APROVADA = "COMPRA APROVADA"

        fun readSMS(context: Context, message: String): TransactionVO? {

            return when {
                message.startsWith(SMS_TITLE_DEBIT) -> readSMSTextDebit(context, message)
                message.startsWith(SMS_TITLE_PAYMENT) -> readSMSTextPayment(message, context.resources.getString(R.string.paymenttype_debito_key))
                else -> null
            }
        }

        /**
         * Template : ITAU DEBITO: Cartao final 0934 COMPRA APROVADA 11/06 08:11:39 R$ 50,00 Local: POSTO ODE. Consulte também pelo celular www.itau.com.br
         */
        fun readSMSTextDebit(context: Context, message: String): TransactionVO {

            val vo = TransactionVO()
            vo.paymentType = PaymentTypeVO()
            vo.paymentType.key = context.resources.getString(R.string.paymenttype_debito_key)

            vo.tag = TagVO()
            vo.tag.key = "TAG_UNKNOWN"

            val localIndexStart = message.indexOf(DEBIT_STR_LOCAL)
            val localIndexEnd = message.indexOf(".", localIndexStart)
            vo.content = message.substring(localIndexStart + DEBIT_STR_LOCAL.length, localIndexEnd).trim { it <= ' ' }

            val currencyIndex = message.indexOf(DEBIT_STR_RS)
            val price = message.substring(currencyIndex + DEBIT_STR_RS.length, localIndexStart).trim { it <= ' ' }.replace(",", ".")
            vo.price = java.lang.Double.valueOf(price)

            val dateIndex = message.indexOf(DEBIT_STR_COMPRA_APROVADA)
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val date = message.substring(dateIndex + DEBIT_STR_COMPRA_APROVADA.length, currencyIndex).trim { it <= ' ' }.replace(" ", "/$year ")

            vo.paymentDate = JavaUtils.DateUtil.parse(date, JavaUtils.DateUtil.DD_MM_YYYY_HH_MM_SS)
            return vo
        }

        internal val SMS_TITLE_PAYMENT = "Realizado pagamento de "
        internal val PAYMENT_VALUE_LABEL = " no valor de "
        internal val PAYMENT_ACCOUNT_LABEL = " na sua conta XXX53-3 em "

        /**
         * Template : Realizado pagamento de TITULOS ITAU no valor de R$ 442,77 na sua conta XXX53-3 em 10/10 as 19:48
         */
        fun readSMSTextPayment(message: String, strKeyDebit: String): TransactionVO {

            val vo = TransactionVO()
            vo.paymentType = PaymentTypeVO()
            vo.paymentType.key = strKeyDebit

            vo.tag = TagVO()
            vo.tag.key = "TAG_UNKNOWN"

            var localIndexStart = message.indexOf(SMS_TITLE_PAYMENT) + SMS_TITLE_PAYMENT.length
            var localIndexEnd = message.indexOf(PAYMENT_VALUE_LABEL, localIndexStart)
            vo.content = message.substring(localIndexStart, localIndexEnd).trim { it <= ' ' }

            localIndexStart = message.indexOf(PAYMENT_VALUE_LABEL) + PAYMENT_VALUE_LABEL.length
            localIndexEnd = message.indexOf(PAYMENT_ACCOUNT_LABEL)
            val price = message.substring(localIndexStart, localIndexEnd).trim { it <= ' ' }
            vo.price = JavaUtils.NumberUtil.removeFormat(price)

            val year = Calendar.getInstance().get(Calendar.YEAR)
            localIndexStart = message.indexOf(PAYMENT_ACCOUNT_LABEL) + PAYMENT_ACCOUNT_LABEL.length
            localIndexEnd = localIndexStart + 5
            val date = message.substring(localIndexStart, localIndexEnd).trim { it <= ' ' } + "/$year"
            vo.paymentDate = JavaUtils.DateUtil.parse(date, JavaUtils.DateUtil.DD_MM_YYYY)
            return vo
        }
    }
}