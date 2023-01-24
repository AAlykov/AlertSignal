package com.tomsk.alykov.alertsignal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

class StartOnBootReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AADebug", "onReceive: !!!!!!!!!!!1")
        if (Intent.ACTION_BOOT_COMPLETED == intent!!.action) {

//            Log.d("AADebug", "onReceive: ACTION_BOOT_COMPLETED")
//            val i = Intent(context, MainActivity::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context!!.startActivity(i)

            notifyAfterReboot(context, intent)
        }
    }


    private fun notifyAfterReboot(context: Context?, intent: Intent) {
        if (context != null) {
            Log.d("AADebug", "notifyAfterReboot: ")
            Toast.makeText(context,"Приложение " + context.resources.getString(R.string.app_name) + " запущено",Toast.LENGTH_LONG).show()
        }

        val contentText = "Приложение " + context!!.resources.getString(R.string.app_name) + " запущено"

        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    "AlertSignal",
                    "Уведомления", //категории уведомлений, можно по русски
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        //Log.i("MyDebug", "1 " + debugTimeStr);

        val intentAlertSignalApp = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intentAlertSignalApp, PendingIntent.FLAG_IMMUTABLE)
        val vibrate = longArrayOf(100, 500, 100, 500)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_baseline_crisis_alert_24)
        val notification = NotificationCompat.Builder(context, "AlertSignal") //тут убиваем два зайца = без проверок на апи 26
            .setContentTitle("AlertSignal")
            .setContentText(contentText)
            .setColor(context.resources.getColor(R.color.red, null))
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
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()

        notificationManager.notify(1, notification)

    }


}
