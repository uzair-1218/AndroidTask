package com.example.androidtask.activity

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.androidtask.R
import com.example.androidtask.adapter.ViewPagerAdapter
import com.example.androidtask.databinding.ActivityMainBinding
import com.example.androidtask.recivers.ReminderReciever
import java.util.*

class MainActivity : AppCompatActivity() {
    val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var hour = 0
    private  var minute = 0

    lateinit var adapter:ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = ViewPagerAdapter( supportFragmentManager,lifecycle)

        binding.pager.adapter = adapter

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                if (position==0){

                    binding.homeTab.setBackgroundColor(resources.getColor(R.color.blue))
                    binding.homeTxt.setTextColor(resources.getColor(R.color.white))
                    binding.homeImage.setImageResource(R.drawable.ic_home_fill)
                    binding.favTab.setBackgroundColor(resources.getColor(R.color.white))
                    binding.favouriteTxt.setTextColor(resources.getColor(R.color.blue))
                    binding.favouriteImage.setImageResource(R.drawable.ic_star_line_blue)
                    binding.titleTxt.text = getString(R.string.drinks_recipes)
                } else{

                    binding.homeTab.setBackgroundColor(resources.getColor(R.color.white))
                    binding.homeTxt.setTextColor(resources.getColor(R.color.blue))
                    binding.homeImage.setImageResource(R.drawable.ic_home_line)
                    binding.favTab.setBackgroundColor(resources.getColor(R.color.blue))
                    binding.favouriteTxt.setTextColor(resources.getColor(R.color.white))
                    binding.favouriteImage.setImageResource(R.drawable.ic_star_fill_white)
                    binding.titleTxt.text = getString(R.string.favourite)
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

     binding.favTab.setOnClickListener {

         binding.pager.currentItem = 1
     }
        binding.homeTab.setOnClickListener {

            binding.pager.currentItem=0
        }


        binding.setReminder.setOnClickListener {

        showTimePicker()
        }

    }



    private fun showTimePicker(){

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.reminder_set_layout)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val timePicker:TimePicker = dialog.findViewById(R.id.time_pic)
        val set_remonder:Button = dialog.findViewById(R.id.btn_set)

        timePicker.setOnTimeChangedListener(OnTimeChangedListener { timePicker: TimePicker?, sHour: Int, sMinute: Int ->
            hour = sHour
            minute = sMinute
        })

        set_remonder.setOnClickListener {
            setNotification()
            setAlarm()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("Notify", "Alarm", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Alarm Reminder"
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val call_alarm = Calendar.getInstance()
        val call_now = Calendar.getInstance()
        call_alarm.time = Date()
        call_now.time = Date()
        call_alarm[Calendar.HOUR_OF_DAY] = hour
        call_alarm[Calendar.MINUTE] = minute
        call_alarm[Calendar.SECOND] = 0
        if (call_alarm.before(call_now)) {
            call_alarm.add(Calendar.DATE, 1)
        }
        val i = Intent(this, ReminderReciever::class.java)
        @SuppressLint("UnspecifiedImmutableFlag") val p =
            PendingIntent.getBroadcast(this, 0, i, 0)
        alarmManager[AlarmManager.RTC_WAKEUP, call_alarm.timeInMillis] = p
    }
}