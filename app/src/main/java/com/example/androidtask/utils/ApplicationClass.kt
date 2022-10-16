package com.example.androidtask.utils

import android.app.Application
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs

class ApplicationClass : Application()
{


    override fun onCreate() {
        super.onCreate()



        /**
         * Initialize the Prefs class
         */
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}