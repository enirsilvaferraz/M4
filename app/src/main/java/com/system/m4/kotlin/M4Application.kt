package com.system.m4.kotlin

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Enir on 12/05/2017.
 */

class M4Application : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
