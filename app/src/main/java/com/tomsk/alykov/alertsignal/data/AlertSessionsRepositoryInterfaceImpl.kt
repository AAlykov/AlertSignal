package com.tomsk.alykov.alertsignal.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.data.models.AlertSessionRequestAnswerModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.data.workers.GetDataWorker
import com.tomsk.alykov.alertsignal.data.workers.PingFBWorker
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal

class AlertSessionsRepositoryInterfaceImpl(private val application: Application):AlertSessionsRepositoryInterface {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(application).alertSessionDao()

    override fun getAllAlertSessions(): LiveData<List<AlertSessionModel>> {
        return alertSessionDao.getAllAlertSessions()
    }

    override fun getNotConfirmAlertSession(): LiveData<AlertSessionModel> {
        return alertSessionDao.getNotConfirmAlertSession()
    }

    override suspend fun updateAlertSession(alertSessionModel: AlertSessionModel) {
        return alertSessionDao.updateAlertSession(alertSessionModel)
    }


    override fun getAlertSessionById(sessionCode: String): AlertSessionModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllAlertSession() {
        alertSessionDao.deleteAllAlertSession()
    }

    override suspend fun addAlertSessionRoom(alertSessionModel: AlertSessionModel) {
        alertSessionDao.addAlertSession(alertSessionModel)
    }

    override fun getDataFB() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            GetDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            GetDataWorker.makeRequest()
        )

        val pingFBWorkManager = WorkManager.getInstance(application)
        pingFBWorkManager.enqueueUniqueWork(
            PingFBWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            PingFBWorker.makeRequest()
        )

        /*val workManager2 = WorkManager.getInstance(application)
        workManager2.enqueueUniqueWork(
            GetDataWorker2.NAME,
            ExistingWorkPolicy.REPLACE,
            GetDataWorker2.makeRequest2()
        )*/
    }

    override fun getAlertSessionRequestAnswer(): LiveData<AlertSessionRequestAnswerModel> {
        return alertSessionDao.getAlertSessionRequestAnswer()
    }

    override suspend fun addAlertSessionFB(alertSessionFBModel: AlertSessionFBModel, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        val database = Firebase.database
        val myRef = database.getReference("alert_session")

        /*val idNote = myRef.push().key.toString()
        val mapNote = hashMapOf<String, Any>()
        mapNote["ID_FIREBASE"] = idNote
        mapNote["NAME"] = "naaaame"
        mapNote["TEXT"] = "teeeeext"         */

        myRef.setValue(alertSessionFBModel)
            .addOnSuccessListener {
                onSuccess()
                Log.d("AADebug", "setValue: onSuccess()")
            }.addOnFailureListener {
                Log.d("AADebug", "insert: onFail() " + it.message.toString())
                onFail(it.toString())
            }

        /* myRef.child(idNote)
            .updateChildren(mapNote)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                //showToast(it.message.toString())

            }

        */

    }

    override fun getAllSystemJournal(): LiveData<List<AlertSignalSystemJournal>> {
        TODO("Not yet implemented")
    }

    override fun getTodaySystemJournal(timeUnix: String): LiveData<List<AlertSignalSystemJournal>> {
        return alertSessionDao.getTodaySystemJournal(timeUnix)
    }

}