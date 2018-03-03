package com.system.m4

import android.app.Application
import com.system.m4.infrastructure.JavaUtils

/**
 * Created by Enir on 12/05/2017.
 */

class M4Application : Application() {

    override fun onCreate() {
        super.onCreate()
        JavaUtils.FirebaseUtil.enableOffline(BuildConfig.FLAVOR)
    }
}
