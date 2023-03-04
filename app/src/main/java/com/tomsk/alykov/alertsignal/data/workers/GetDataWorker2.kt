package com.tomsk.alykov.alertsignal.data.workers

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.utils.Calculations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetDataWorker2(val context: Context, workerParameters: WorkerParameters):Worker(context, workerParameters) {

    val database = Firebase.database
    val myRef = database.getReference("alert_session")

    override fun doWork(): Result {

        for (i in 0..10) {
            Log.d("AADebug", "doWork2: $i")

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    //val post = dataSnapshot.getValue<Post>()
                    // ...
                    Log.d("AADebug", "onDataChange: ${dataSnapshot.toString()}")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.d("AADebug", "loadPost:onCancelled", databaseError.toException())
                }
            }
            myRef.addValueEventListener(postListener)


            myRef.get().addOnSuccessListener {
                Log.d("AADebug", "doWork: myRef.get().addOnSuccessListener = $it")

                val sessionCheckTimeUnixLong = System.currentTimeMillis()
                val sessionGetTimeUnixLong = sessionCheckTimeUnixLong
                val sessionCheckTime = Calculations.timeStampToString(sessionCheckTimeUnixLong)

                val sessionCode = it.child("sessionCode").value.toString()
                val senderName = it.child("senderName").value.toString()
                val signalName = it.child("signalName").value.toString()
                val signalType = it.child("signalType").value.toString()
                val signalTypeInt = signalType.toInt()
                val signalGrade = it.child("signalGrade").value.toString()
                val signalGradeInt = signalGrade.toInt()
                val signalText = it.child("signalText").value.toString()
                val sessionStartTimeUnix = it.child("sessionStartTimeUnix").value.toString()
                val sessionIdFireBase = it.child("sessionIdFireBase").value.toString()

                val res = it.getValue(AlertSessionFBModel::class.java)


            }.addOnFailureListener {
                val sessionCheckTimeUnixLong = System.currentTimeMillis()
                val sessionCheckTime = Calculations.timeStampToString(sessionCheckTimeUnixLong)

                val errorString = it.toString()
                Log.d("AADebug", "doWork: addOnFailureListener errorString = $errorString")

            }
            myRef.get().addOnFailureListener {
                val errorString = it.toString()
                Log.d("AADebug", "doWork: addOnFailureListener errorString = $errorString")
            }



            Thread.sleep(10000)
        }

        /*while (true) {

        }*/
        return Result.retry()
    }

    companion object {
        const val NAME = "GetDataWorker2"
        fun makeRequest2(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<GetDataWorker2>().build()
        }
    }


}