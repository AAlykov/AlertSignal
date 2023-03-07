package com.tomsk.alykov.alertsignal.data.workers

import android.app.Application
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.data.firebase.AlertSessionFirebaseInterfaceImpl
import com.tomsk.alykov.alertsignal.data.firebase.AlertSessionsFirebaseInterface
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.presentation.MainActivity
import com.tomsk.alykov.alertsignal.utils.Calculations.timeStampToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GetDataWorker(val context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(context).alertSessionDao()
    private val alertSessionFirebaseInterfaceImpl = AlertSessionFirebaseInterfaceImpl(context as Application)

    //var res = ""
    val database = Firebase.database
    val myRef = database.getReference("alert_session")
    val auth = FirebaseAuth.getInstance()

    init {

    }



    override suspend fun doWork(): Result {
        Log.d("AADebug", "doWork: Begin")
        //val sessionCheckTimeUnixLong
        //val sessionCheckTime = ""
        val errorCheck = ""
        while (true) {
            try {  //самая простая обработка ошибки при работе с корутинами
                Log.d("AADebug", "doWork: try")

                /*val alertSessionCheckModel = AlertSessionCheckModel(
                    "001/1234", "Объект 178", "Техническая проверка системы оповещения",
                    1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                    "20230117 12:40:01","",  "", ""
                )*/

                /*var alertSessionFBModel = AlertSessionFBModel(
                    "001/1235", "Объект 178", "Техническая проверка системы оповещения",
                    1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                    "1678115277999", "06.03.2023 22:07:57", "")
                myRef.setValue(alertSessionFBModel)
                */


                auth.signInAnonymously().addOnSuccessListener {
                    myRef.get().addOnSuccessListener {
                        Log.d("AADebug", "doWork: myRef.get().addOnSuccessListener = $it")

                        val sessionCheckTimeUnixLong = System.currentTimeMillis()
                        val sessionGetTimeUnixLong = sessionCheckTimeUnixLong
                        val sessionGetTime = timeStampToString(sessionGetTimeUnixLong)

                        val sessionCheckTime = timeStampToString(sessionCheckTimeUnixLong)

                        val sessionCode = it.child("sessionCode").value.toString()
                        val senderName = it.child("senderName").value.toString()
                        val signalName = it.child("signalName").value.toString()
                        val signalType = it.child("signalType").value.toString()
                        val signalTypeInt = signalType.toInt()
                        val signalGrade = it.child("signalGrade").value.toString()
                        val signalGradeInt = signalGrade.toInt()
                        val signalText = it.child("signalText").value.toString()
                        val sessionStartTimeUnix = it.child("sessionStartTimeUnix").value.toString()
                        val sessionStartTime = it.child("sessionStartTime").value.toString()
                        val sessionIdFireBase = it.child("sessionIdFireBase").value.toString()

                        val res = it.getValue(AlertSessionFBModel::class.java)

                        CoroutineScope(Dispatchers.IO).launch {
                            val alertSessionCheckModel = AlertSessionCheckModel(0, sessionCode, senderName,
                                signalName, signalTypeInt, signalGradeInt, signalText, sessionStartTimeUnix,
                                sessionCheckTimeUnixLong.toString(), sessionCheckTime, errorCheck, sessionIdFireBase)
                            alertSessionDao.updateAlertSessionCheck(alertSessionCheckModel)

                            val alertSessionModel = alertSessionDao.checkAlertSession(sessionCode)

                            if (alertSessionModel != null) {
                                Log.d("AADebug", "doWork: alertSessionDao.checkAlertSession(sessionCode) = $alertSessionModel")
                            } else {
                                Log.d("AADebug", "doWork: alertSessionDao.checkAlertSession(sessionCode) = $alertSessionModel NO")
                                val alertSessionModel = AlertSessionModel(
                                    0, sessionCode, senderName, signalName, signalTypeInt, signalGradeInt,
                                    signalText, sessionStartTimeUnix, sessionStartTime, sessionGetTimeUnixLong.toString(),
                                    sessionGetTime, "", "", "")
                                alertSessionDao.addAlertSession(alertSessionModel)
                                notifyGetDataWorker(context, "$sessionCode $signalName")
                            }
                        }

                    }.addOnFailureListener {
                        val sessionCheckTimeUnixLong = System.currentTimeMillis()
                        val sessionCheckTime = timeStampToString(sessionCheckTimeUnixLong)

                        val errorString = it.toString()
                        Log.d("AADebug", "doWork: addOnFailureListener errorString = $errorString")
                        val alertSessionCheckModel = AlertSessionCheckModel(0, "", "",
                            "", 0, 0, "", "",
                            sessionCheckTimeUnixLong.toString(), sessionCheckTime, errorString, "")
                        CoroutineScope(Dispatchers.IO).launch {
                            alertSessionDao.updateAlertSessionCheck(alertSessionCheckModel)
                        }
                    }

                }.addOnFailureListener {
                    val sessionCheckTimeUnixLong = System.currentTimeMillis()
                    val sessionCheckTime = timeStampToString(sessionCheckTimeUnixLong)

                    val errorString = it.toString()
                    Log.d("AADebug", "doWork: addOnFailureListener errorString = $errorString")
                    val alertSessionCheckModel = AlertSessionCheckModel(0, "", "",
                        "", 0, 0, "", "",
                        sessionCheckTimeUnixLong.toString(), sessionCheckTime, errorString, "")
                    CoroutineScope(Dispatchers.IO).launch {
                        alertSessionDao.updateAlertSessionCheck(alertSessionCheckModel)
                    }
                }

                myRef.get().addOnFailureListener {
                    //val errorString = it.toString()
                    //Log.d("AADebug", "doWork: addOnFailureListener errorString = $errorString")
                }
                //Log.d("AADebug", "doWork: " + getDataFB(alertSessionFirebaseInterfaceImpl))

                val alertSessionModel = AlertSessionModel(0, "001/1234", "Объект 178","Техническая проверка системы оповещения",
                    1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                    "20230117 12:40:01", "20230117 12:40:10", "", "", "", "")
                //alertSessionDao.addAlertSession(alertSessionModel)

                //notifyGetDataWorker(context)

/*
                val locListener: ValueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //do stuff here
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("AADebug", "onCancelled: $error.toString()")
                    }
                }
                myRef.addValueEventListener(locListener)
*/



            } catch (e: Exception) {
                Log.d("AADebug", "doWork: Exception" + e.toString())
            }
            delay(20000)
            Log.d("AADebug", "doWork: delay 20000")
        }

    }

    private fun notifyGetDataWorker(context: Context, signalName: String) {
        val contentText = signalName
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
            .setContentTitle("Получен сигнал оповещения")
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

    fun getDataFB(alertSessionsFirebaseInterface: AlertSessionsFirebaseInterface): String { //AlertSessionModel {
        return alertSessionsFirebaseInterface.getAlertSessionFB()
    }
}



