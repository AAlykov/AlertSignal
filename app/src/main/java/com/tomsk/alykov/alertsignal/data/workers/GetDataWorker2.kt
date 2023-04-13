package com.tomsk.alykov.alertsignal.data.workers

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class GetDataWorker2(val context: Context, workerParameters: WorkerParameters):Worker(context, workerParameters) {

    val database = Firebase.database
    val myRef = database.getReference("alert_session")

    override fun doWork(): Result {

        for (i in 0..10) {
            Log.d("AADebug", "doWork2: $i")

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