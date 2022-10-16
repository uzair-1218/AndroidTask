package com.example.androidtask.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes

object AppUtils {
    private var mToast: Toast? = null

    private fun createToast(string: String?, toastDuration: Int,context:Context) {
        mToast?.cancel()
        mToast = Toast.makeText(context, string, toastDuration)
        Handler(Looper.getMainLooper()).post {
            try {
                mToast?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun showToast(toastMessage: String,context: Context) {
        createToast(toastMessage, Toast.LENGTH_SHORT, context)
    }

    fun showToast(@StringRes resId: Int,context:Context) {
        createToast(context.getString(resId), Toast.LENGTH_SHORT,context)
    }

    var BY_NAME_KEY:String = "by_name"
    var By_ALPHABET:String = "by_alphabet"



}