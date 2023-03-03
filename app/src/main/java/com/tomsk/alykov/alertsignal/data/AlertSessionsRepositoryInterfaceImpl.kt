package com.tomsk.alykov.alertsignal.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class AlertSessionsRepositoryInterfaceImpl(private val application: Application):AlertSessionsRepositoryInterface {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(application).alertSessionDao()

    override fun getAllAlertSessions(): LiveData<List<AlertSessionModel>> {
        return alertSessionDao.getAllAlertSessions()
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
        alertSessionDao.addAlertSession(alertSessionModel)
    }
}