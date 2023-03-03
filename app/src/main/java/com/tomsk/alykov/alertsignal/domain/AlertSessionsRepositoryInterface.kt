package com.tomsk.alykov.alertsignal.domain

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

interface AlertSessionsRepositoryInterface {
    fun getAllAlertSessions(): LiveData<List<AlertSessionModel>>
    fun getNotConfirmAlertSession(): LiveData<AlertSessionModel>
    fun confirmAlertSession(sessionCode: String): Boolean
    fun getAlertSessionById(sessionCode: String): AlertSessionModel

    suspend fun setTest(alertSessionModel: AlertSessionModel)

    //fun loadData()
}