package com.example.androidtask.recievers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import java.util.*

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent!!.getAction())) {
            setNotification(p0!!)
            setAlarm(p0)
        }
    }


    private fun setNotification(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("Notify", "Alarm", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Alarm Reminder"
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun setAlarm(context: Context) {
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val call_alarm = Calendar.getInstance()
        val call_now = Calendar.getInstance()
        call_alarm.time = Date()
        call_now.time = Date()
        call_alarm[Calendar.HOUR_OF_DAY] = Prefs.getInt("Hours")
        call_alarm[Calendar.MINUTE] = Prefs.getInt("MINUTES")
        call_alarm[Calendar.SECOND] = 0
        if (call_alarm.before(call_now)) {
            call_alarm.add(Calendar.DATE, 1)
        }
        val i = Intent(context, ReminderReciever::class.java)
        @SuppressLint("UnspecifiedImmutableFlag") val p =
            PendingIntent.getBroadcast(context, 0, i, 0)
        alarmManager[AlarmManager.RTC_WAKEUP, call_alarm.timeInMillis] = p
    }
}