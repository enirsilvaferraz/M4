package com.system.m4.kotlin.services

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.infrastructure.listeners.PersistenceListener
import com.system.m4.kotlin.transaction.TransactionBusiness
import com.system.m4.views.vos.PaymentTypeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
import java.util.*


/**
 * Created by enirs on 30/09/2017.
 * Service for Notification
 *
 * Ex.: Compra de R$ 10,00 APROVADA em Cabify
 */
class NotificationReceiver : NotificationListenerService() {

    private val APROVADA = "APROVADA em "
    private val RS = "R$"
    private val SMS_TITLE = "Nubank"

    private val CONFIG_TITLE = "android.title"
    private val CONFIG_BODY = "android.text"

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn != null) {

            val extras = sbn.notification.extras

            if (extras.getString(CONFIG_TITLE, "").contains(SMS_TITLE)) {
                val text = extras.getString(CONFIG_BODY)
                val value = JavaUtils.NumberUtil.removeFormat(text.subSequence(text.indexOf(RS), text.indexOf(APROVADA)).trim() as String?)
                val content = text.subSequence(text.indexOf(APROVADA) + APROVADA.length, text.length) as String
                save(getTransaction(value, content))
            }
        }
    }

    private fun save(vo: TransactionVO) {
        TransactionBusiness.save(vo, object : PersistenceListener<TransactionVO> {
            override fun onSuccess(model: TransactionVO) {}
            override fun onError(error: String) {}
        })
    }

    private fun getTransaction(value: Double, content: String): TransactionVO {

        val vo = TransactionVO()
        vo.purchaseDate = Calendar.getInstance().time
        vo.price = value
        vo.content = content.trim()

        vo.paymentType = PaymentTypeVO()
        vo.paymentType.key = resources.getString(R.string.paymenttype_nubank_key)

        vo.tag = TagVO()
        vo.tag.key = "TAG_UNKNOWN"

        val calendar = Calendar.getInstance()
        if (calendar.get(Calendar.DATE) > 16) {
            calendar.add(Calendar.MONTH, 1)
        }
        calendar.set(Calendar.DATE, 23)
        vo.paymentDate = calendar.time
        return vo
    }
}