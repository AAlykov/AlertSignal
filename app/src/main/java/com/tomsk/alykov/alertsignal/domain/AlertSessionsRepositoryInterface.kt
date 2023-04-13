package com.tomsk.alykov.alertsignal.domain

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.data.models.AlertSessionRequestAnswerModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal

interface AlertSessionsRepositoryInterface {
    fun getAllAlertSessions(): LiveData<List<AlertSessionModel>>

    fun getNotConfirmAlertSession(): LiveData<AlertSessionModel>
    fun getAlertSessionById(sessionCode: String): AlertSessionModel

    suspend fun updateAlertSession(alertSessionModel: AlertSessionModel)//fun confirmAlertSession(sessionCode: String): Boolean
    suspend fun deleteAllAlertSession()
    suspend fun addAlertSessionRoom(alertSessionModel: AlertSessionModel)
    suspend fun addAlertSessionFB(alertSessionFBModel: AlertSessionFBModel, onSuccess: () -> Unit, onFail: (String) -> Unit)

    fun getDataFB()

    fun getAlertSessionRequestAnswer(): LiveData<AlertSessionRequestAnswerModel>

    fun getAllSystemJournal(): LiveData<List<AlertSignalSystemJournal>>
    fun getTodaySystemJournal(timeUnix: String): LiveData<List<AlertSignalSystemJournal>>
}