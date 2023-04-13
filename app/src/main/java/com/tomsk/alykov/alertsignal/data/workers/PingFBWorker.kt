package com.tomsk.alykov.alertsignal.data.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.data.Utils.writeToSystemJournal
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBPing
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.utils.Calculations
import kotlinx.coroutines.delay
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

class PingFBWorker (val context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {
    private val alertSessionDao = AlertSessionDatabase.getDatabase(context).alertSessionDao()

    val database = Firebase.database
    val myRefPing = database.getReference("alert_session_ping")
    val auth = FirebaseAuth.getInstance()
    private val setDelayPing = 30
    private val checkPeriodPing = 30

    override suspend fun doWork(): Result {
        Log.d("AADebug", "PingFBWorker doWork: Begin")
        val errorCheck = ""
        while (true) {
            var nowTimeUnix = System.currentTimeMillis()
            var rememberTime: Long = 0
            var uidCurrentUser = FirebaseAuth.getInstance().currentUser?.uid?:""
            val stamp = Timestamp(nowTimeUnix)
            val sdf = SimpleDateFormat("ss")
            val sdfMM = SimpleDateFormat("mm")
            val dateSS = sdf.format(Date(stamp.time))
            if (dateSS.toString() == "00") {
                while (true) {
                    try { Log.d("AADebug", "PingFBWorker doWork: try")
                        nowTimeUnix = System.currentTimeMillis()
                        if (rememberTime == 0L) {
                            val myId = uidCurrentUser//myRefPing.push().getKey()
                            rememberTime = nowTimeUnix
                            val nowTime = Calculations.timeStampToString(nowTimeUnix)
                            var alertSessionFBPing = AlertSessionFBPing(myId,nowTimeUnix.toString(),nowTime,getDeviceInfo())

                            writeToSystemJournal(alertSessionDao,"PingFBWorker Trying Set: myRefPing.child(uidCurrentUser).setValue(alertSessionFBPing).addOnSuccessListener")
                            myRefPing.child(uidCurrentUser).setValue(alertSessionFBPing).addOnSuccessListener {
                                writeToSystemJournal(alertSessionDao,"PingFBWorker Success Set: myRefPing.child(uidCurrentUser).setValue(alertSessionFBPing).addOnSuccessListener")
                            }.addOnFailureListener {
                                val errorString = it.toString()
                                writeToSystemJournal(alertSessionDao,"PingFBWorker Problem Set: $errorString myRefPing.child(uidCurrentUser).setValue(alertSessionFBPing).addOnSuccessListener")
                            }
                        }
                        else {
                            writeToSystemJournal(alertSessionDao,"PingFBWorker Trying Get: myRefPing.child(uidCurrentUser).get().addOnSuccessListener {")
                            myRefPing.child(uidCurrentUser).get().addOnSuccessListener {
                                //Log.d("AADebug", "PingFBWorker doWork: myRefPing.get().addOnSuccessListener = $it")
                                writeToSystemJournal(alertSessionDao,"PingFBWorker Success Get: myRefPing.child(uidCurrentUser).get().addOnSuccessListener {")
                                val pingUnixTime = it.child("pingTimeUnix").value.toString().toLong()
                                nowTimeUnix = System.currentTimeMillis()
                                val delayBetweenNowAndSet = nowTimeUnix - pingUnixTime
                                if (delayBetweenNowAndSet/1000 > setDelayPing) {
                                    writeToSystemJournal(alertSessionDao,"PingFBWorker Problem: delay ${delayBetweenNowAndSet/1000} sec, SetDelay = $setDelayPing")
                                }
                                rememberTime = 0
                            }.addOnFailureListener {
                                val errorString = it.toString()
                                writeToSystemJournal(alertSessionDao,"PingFBWorker Problem Get: $errorString myRefPing.child(uidCurrentUser).get().addOnSuccessListener {")
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("AADebug", "doWork: Exception" + e.toString())
                    }
                    delay (checkPeriodPing.toLong() * 1000)
                    Log.d("AADebug", "doWork: delay $checkPeriodPing")
                    val stamp = Timestamp(nowTimeUnix)
                    val dateMM = sdfMM.format(Date(stamp.time))
                    if (dateMM.toString() == "00") {
                        break
                    }
                }
            }
            delay(500)
        }

    }


    private fun getDeviceInfo(): String {
        val manufacturer = Build.MANUFACTURER //Google
        val model = Build.MODEL //Xiaomi 2109119DG
        val brand = Build.BRAND //Xiaomi
        val user = Build.USER //builder
        val device = Build.DEVICE //lisa
        return "$manufacturer $model"
    }

    companion object {
        const val NAME = "PingFBWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<PingFBWorker>().build()
        }
    }

}