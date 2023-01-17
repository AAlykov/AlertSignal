package com.tomsk.alykov.alertsignal.database.room

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.models.AlertSessionModel

class AlertSessionRepository(private val alertSessionDao: AlertSessionDao) {

    val getAllAlertSessions: LiveData<List<AlertSessionModel>> = alertSessionDao.getAllAlertSessions()

    suspend fun addAlertSession(alertSessionModel: AlertSessionModel) {
        alertSessionDao.addAlertSession(alertSessionModel)
    }

}