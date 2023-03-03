package com.tomsk.alykov.alertsignal.data.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.presentation.MainActivity
import kotlinx.coroutines.delay

class GetDataWorker(val context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(context).alertSessionDao()

    override suspend fun doWork(): Result {
        Log.d("AADebug", "doWork: Begin")
        while (true) {
            try {  //самая простая обработка ошибки при работе с корутинами
                Log.d("AADebug", "doWork: try")

                val alertSessionModel = AlertSessionModel(0, "001/1234", "Объект 178","Техническая проверка системы оповещения",
                    1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                    "20230117 12:40:01", "20230117 12:40:10", "", "", "", "")
                alertSessionDao.addAlertSession(alertSessionModel)

                notifyGetDataWorker(context)


            } catch (e: Exception) {
                Log.d("AADebug", "doWork: Exception" + e.toString())
            }
            delay(20000)
            Log.d("AADebug", "doWork: delay 20000")
        }

    }

    private fun notifyGetDataWorker(context: Context) {
        val contentText = "Получен сигнал оповещения"
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        val bitmap = BitmapFactory.decodeResource(context.resources,
            R.drawable.ic_baseline_crisis_alert_24
        )
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

    companion object {
        const val NAME = "GetDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<GetDataWorker>().build()
        }
    }
}

