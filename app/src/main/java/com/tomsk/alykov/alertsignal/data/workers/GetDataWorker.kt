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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.data.Utils
import com.tomsk.alykov.alertsignal.data.Utils.timeStampToString
import com.tomsk.alykov.alertsignal.data.firebase.AlertSessionFirebaseInterfaceImpl
import com.tomsk.alykov.alertsignal.data.firebase.AlertSessionsFirebaseInterface
import com.tomsk.alykov.alertsignal.data.models.AlertSessionRequestAnswerModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal
import com.tomsk.alykov.alertsignal.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat


class GetDataWorker(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(context).alertSessionDao()
    private val alertSessionFirebaseInterfaceImpl =
        AlertSessionFirebaseInterfaceImpl(context as Application)

    //var res = ""
    val database = Firebase.database
    val myRef = database.getReference("alert_session")
    val myRefPing = database.getReference("alert_session_ping")
    val auth = FirebaseAuth.getInstance()
    private val setCheckDelay = 20
    private val setCheckPeriod = 30
    var firstStart = true

    override suspend fun doWork(): Result {
        //Log.d("AADebug", "doWork: Begin")
        writeToSystemJournal("GetDataWorker doWork Begin")
        val errorCheck = ""
        while (true) {
            val stamp = Timestamp(System.currentTimeMillis())
            val sdf = SimpleDateFormat("ss")
            val date = sdf.format(Date(stamp.time))
            if ((firstStart) || date.toString() == "00" && !firstStart) {
                writeToSystemJournal("GetDataWorker doWork try {")
                while (true) {
                    try {
                        val sessionCheckTimeUnixLongCommon = System.currentTimeMillis()
                        val requestTime = timeStampToString(sessionCheckTimeUnixLongCommon)

                        //val alertSessionRequestAnswerModel = AlertSessionRequestAnswerModel(0, sessionCheckTimeUnixLongCommon.toString(), requestTime,"", "", "", "")
                        CoroutineScope(Dispatchers.IO).launch {
                            alertSessionDao.updateAlertSessionRequest(sessionCheckTimeUnixLongCommon.toString(), requestTime)
                        }

                        writeToSystemJournal("GetDataWorker Trying Get: myRef.get().addOnSuccessListener {")
                        auth.signInAnonymously().addOnSuccessListener {
                            myRef.get().addOnSuccessListener {
                                //Log.d("AADebug", "doWork: myRef.get().addOnSuccessListener = $it")
                                writeToSystemJournal("GetDataWorker Success Get: myRef.get().addOnSuccessListener {")
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
                                    //val alertSessionRequestAnswerModel = AlertSessionRequestAnswerModel(0, answerTimeUnix = sessionGetTimeUnixLong.toString(), answerTime = sessionCheckTime)
                                    alertSessionDao.updateAlertSessionAnswer(sessionGetTimeUnixLong.toString(),sessionCheckTime)
                                    alertSessionDao.updateAlertSessionRequestAnswerError("")
                                    /*val alertSessionCheckModel = AlertSessionCheckModel(
                                        0,sessionCode,senderName,signalName,signalTypeInt,signalGradeInt,signalText,
                                        sessionStartTimeUnix,sessionCheckTimeUnixLong.toString(),sessionCheckTime,errorCheck,sessionIdFireBase)
                                    alertSessionDao.updateAlertSessionCheck(alertSessionCheckModel) */

                                    var idAlertSessionModel = 0
                                    idAlertSessionModel = alertSessionDao.checkAlertSession(sessionCode)
                                    if (idAlertSessionModel == 0) {
                                        val alertSessionModel = AlertSessionModel(0,sessionCode,senderName,signalName,signalTypeInt,signalGradeInt,
                                            signalText,sessionStartTimeUnix,sessionStartTime,sessionGetTimeUnixLong.toString(),sessionGetTime,"",
                                            "","")
                                        alertSessionDao.addAlertSession(alertSessionModel)
                                        notifyGetDataWorker(context, "$sessionCode $signalName")
                                    }
                                } //CoroutineScope(Dispatchers.IO).launch

                            }.addOnFailureListener {
                                val sessionCheckTimeUnixLong = System.currentTimeMillis()
                                val sessionCheckTime = timeStampToString(sessionCheckTimeUnixLong)
                                val errorString = it.toString()
                                Log.d("AADebug","doWork: addOnFailureListener errorString = $errorString")
                                CoroutineScope(Dispatchers.IO).launch {
                                    alertSessionDao.updateAlertSessionAnswer(sessionCheckTimeUnixLong.toString(),sessionCheckTime)
                                    alertSessionDao.updateAlertSessionRequestAnswerError(errorCheck)
                                }
                            }

                        }.addOnFailureListener {
                            val sessionCheckTimeUnixLong = System.currentTimeMillis()
                            val sessionCheckTime = timeStampToString(sessionCheckTimeUnixLong)
                            val errorString = it.toString()
                            Log.d("AADebug","doWork: addOnFailureListener errorString = $errorString")
                            CoroutineScope(Dispatchers.IO).launch {
                                alertSessionDao.updateAlertSessionAnswer(sessionCheckTimeUnixLong.toString(),sessionCheckTime)
                                alertSessionDao.updateAlertSessionRequestAnswerError(errorCheck)
                            }
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            delay(setCheckPeriod/2.toLong() * 1000)
                            val alertSessionRequestAnswerModel = alertSessionDao.getAlertSessionRequestAnswerModel()
                            if (alertSessionRequestAnswerModel.answerTimeUnix == "") {
                                val sessionCheckTimeUnixLong = System.currentTimeMillis()
                                val sessionCheckTime = timeStampToString(sessionCheckTimeUnixLong)
                                val errorString = "время (${setCheckPeriod/2} сек) ожидания ответа от сервера истекло"
                                Log.d("AADebug","doWork: addOnFailureListener errorString = $errorString")
                                alertSessionDao.updateAlertSessionRequestAnswerError(errorString)
                                alertSessionDao.updateAlertSessionAnswer(sessionCheckTimeUnixLong.toString(),sessionCheckTime)
                                writeToSystemJournal("GetDataWorker Problem Server Answer Time (${setCheckPeriod/2} sec) out")
                            }
                        }

                        myRef.get().addOnFailureListener {
                            //val errorString = it.toString()
                            //Log.d("AADebug", "doWork: addOnFailureListener errorString = $errorString")
                        }
                        //Log.d("AADebug", "doWork: " + getDataFB(alertSessionFirebaseInterfaceImpl))


                        /*val alertSessionModel = AlertSessionModel(
                            0, "001/1234", "Объект 178", "Техническая проверка системы оповещения",
                            1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                            "20230117 12:40:01", "20230117 12:40:10", "", "", "", ""
                        ) */
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
                    if (firstStart) {
                        firstStart = false
                        break
                    }
                    delay(setCheckPeriod.toLong() * 1000)
                    //Log.d("AADebug", "doWork: delay 20000")
                }
            }
            delay(500)
        }

    }

    private fun notifyGetDataWorker(context: Context, signalName: String) {
        val contentText = signalName
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intentAlertSignalApp,
            PendingIntent.FLAG_IMMUTABLE
        )
        val vibrate = longArrayOf(100, 500, 100, 500)
        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_baseline_crisis_alert_24
        )
        val notification = NotificationCompat.Builder(
            context,
            "AlertSignal"
        ) //тут убиваем два зайца = без проверок на апи 26
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

    private fun writeToSystemJournal(s: String) {
        val nowTimeUnix = System.currentTimeMillis()
        val nowTime = Utils.timeStampToStringSSS(nowTimeUnix)
        val alertSignalSystemJournal = AlertSignalSystemJournal(0, nowTimeUnix.toString(),nowTime,s)
        CoroutineScope(Dispatchers.IO).launch {
            alertSessionDao.addAlertSignalSystemJournal(alertSignalSystemJournal)
        }
    }
}



