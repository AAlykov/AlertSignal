package com.tomsk.alykov.alertsignal

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.tomsk.alykov.alertsignal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView)

        setupActionBarWithNavController(navController)

        binding.buttonNotify.setOnClickListener {

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(
                        "AlertSignal",
                        "Уведомления", //категории уведомлений, можно по русски
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                notificationManager.createNotificationChannel(notificationChannel)
            }
            val intentAlertSignalApp = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intentAlertSignalApp, PendingIntent.FLAG_IMMUTABLE)
            val vibrate = longArrayOf(100, 500, 100, 500)
            val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.ic_baseline_crisis_alert_24)
            val notification = NotificationCompat.Builder(this, "AlertSignal") //тут убиваем два зайца = без проверок на апи 26
                .setContentTitle("AlertSignal")
                .setContentText("Text")
                .setColor(this.resources.getColor(R.color.red, null))
                .setShowWhen(true)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setVibrate(vibrate)
                .setLights(Color.GREEN, 2000, 3000)
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setSmallIcon(R.drawable.ic_baseline_crisis_alert_24)
                .setContentIntent(pendingIntent)
                //.setContentIntent(PendingIntent.getActivity(this,0, Intent(this, MainActivity::class.java),0))
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build()

            //val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp()
        return NavigationUI.navigateUp(navController, null)
    }
}