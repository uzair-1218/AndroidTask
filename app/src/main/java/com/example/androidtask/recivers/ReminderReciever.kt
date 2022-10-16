package com.example.androidtask.recivers


import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Vibrator
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidtask.R
import com.example.androidtask.activity.MainActivity
import com.example.androidtask.room.DatabaseClass
import java.util.*


class ReminderReciever : BroadcastReceiver() {
    @SuppressLint("RemoteViewLayout")
    override fun onReceive(context: Context, intent: Intent?) {
        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        @SuppressLint("UnspecifiedImmutableFlag") val p =
            PendingIntent.getActivity(context, 0, i, 0)
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(2000)

        val databaseClass = DatabaseClass(context)

        if (databaseClass.drinkdao().getAllData().size > 0){

            val randomGenerator: Random = Random()
            val index = randomGenerator.nextInt(databaseClass.drinkdao().getAllData().size)
            val entity = databaseClass.drinkdao().getAllData().get(index)
            val mBuilder = NotificationCompat.Builder(context, "Notify")
            val contentView = RemoteViews(context.packageName, R.layout.recycler_signle_item)
            contentView.setImageViewBitmap(R.id.drink_image,entity.drink_image)
            contentView.setTextViewText(R.id.drink_name_txt,entity.drink_name)
            contentView.setTextViewText(R.id.drink_des,entity.drink_description)
            contentView.setOnClickPendingIntent(R.id.main_content_lay, p)
            mBuilder.setSmallIcon(R.drawable.ic_alarm)
            mBuilder.setAutoCancel(false)
            mBuilder.setOngoing(true)
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH)
            mBuilder.setOnlyAlertOnce(true)
            mBuilder.setDefaults(NotificationCompat.PRIORITY_HIGH)
            mBuilder.setContent(contentView)
            val n = NotificationManagerCompat.from(context)
            n.notify(200, mBuilder.build())

        }else{
            val b: NotificationCompat.Builder = NotificationCompat.Builder(context, "Notify")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Reminder")
                .setContentText("Need some drinks open app now")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.PRIORITY_HIGH)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(p)
            val n = NotificationManagerCompat.from(context)
            n.notify(200, b.build())
        }

        val s = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val r = RingtoneManager.getRingtone(context, s)
        r.play()
    }
}