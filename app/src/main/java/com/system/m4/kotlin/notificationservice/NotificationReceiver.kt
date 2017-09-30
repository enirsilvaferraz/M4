package com.system.m4.kotlin.notificationservice

import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification


/**
 * Created by enirs on 30/09/2017.
 * Service for Notification
 */
class NotificationReceiver : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn != null) {

            val extras = sbn.notification.extras
            log(extras.toString())

            if (extras.getString("android.title").contains("Nubank")) {
                val text = extras.getString("android.text")
            }
        }
    }

    private fun log(log: String) {

        val sharedPref = getSharedPreferences("SHARED_PREF_M4", Context.MODE_PRIVATE)

        val logShared = sharedPref.getString("NOTIFICATION_M4", "") + "\n\n" + log

        val editor = sharedPref.edit()
        editor.putString("NOTIFICATION_M4", logShared)
        editor.apply()
    }
}