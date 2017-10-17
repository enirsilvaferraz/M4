package com.system.m4.kotlin.services

import android.content.Context
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

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn != null) {

            val extras = sbn.notification.extras

            if (extras.getString("android.title", "").contains("Nubank")) {
                val text = extras.getString("android.text")
                val value = JavaUtils.NumberUtil.removeFormat(text.subSequence(text.indexOf("R$"), text.indexOf("APROVADA")).trim() as String?)
                val content = text.subSequence(text.indexOf("APROVADA") + "APROVADA".length, text.length) as String

                save(value, content)
            }
        }
    }

    private fun save(value: Double, content: String) {

        val vo = TransactionVO()
        vo.purchaseDate = Calendar.getInstance().time
        vo.price = value
        vo.content = content

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

        TransactionBusiness.save(vo, object : PersistenceListener<TransactionVO> {
            override fun onSuccess(model: TransactionVO) {}
            override fun onError(error: String) {}
        })
    }

    private fun log(log: String) {

        val sharedPref = getSharedPreferences("SHARED_PREF_M4", Context.MODE_PRIVATE)

        val logShared = sharedPref.getString("NOTIFICATION_M4", "") + "\n\n" + log

        val editor = sharedPref.edit()
        editor.putString("NOTIFICATION_M4", logShared)
        editor.apply()
    }
}