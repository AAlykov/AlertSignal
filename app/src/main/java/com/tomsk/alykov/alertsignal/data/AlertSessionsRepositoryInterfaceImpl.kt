package com.tomsk.alykov.alertsignal.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.data.workers.GetDataWorker
import com.tomsk.alykov.alertsignal.data.workers.GetDataWorker2
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.utils.Calculations

class AlertSessionsRepositoryInterfaceImpl(private val application: Application):AlertSessionsRepositoryInterface {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(application).alertSessionDao()

    override fun getAllAlertSessions(): LiveData<List<AlertSessionModel>> {
        return alertSessionDao.getAllAlertSessions()
    }

    override fun getAlertSessionCheck(): LiveData<AlertSessionCheckModel> {
        return alertSessionDao.getAlertSessionCheck()
    }

    override fun getNotConfirmAlertSession(): LiveData<AlertSessionModel> {
        return alertSessionDao.getNotConfirmAlertSession()
    }


    override fun confirmAlertSession(sessionCode: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAlertSessionById(sessionCode: String): AlertSessionModel {
        TODO("Not yet implemented")
    }

    override suspend fun setTest(alertSessionModel: AlertSessionModel) {
        //alertSessionDao.addAlertSession(alertSessionModel)
        alertSessionDao.deleteAllAlertSession()//addAlertSession(alertSessionModel)
    }

    override fun getDataFB() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            GetDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            GetDataWorker.makeRequest()
        )

        /*val workManager2 = WorkManager.getInstance(application)
        workManager2.enqueueUniqueWork(
            GetDataWorker2.NAME,
            ExistingWorkPolicy.REPLACE,
            GetDataWorker2.makeRequest2()
        )*/
    }
}