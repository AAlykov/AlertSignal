package com.tomsk.alykov.alertsignal.domain

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel2
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

interface AlertSessionsRepositoryInterface {
    fun getAllAlertSessions(): LiveData<List<AlertSessionModel>>
    fun getAlertSessionCheck(): LiveData<AlertSessionCheckModel>
    fun getAlertSessionCheck2(): LiveData<AlertSessionCheckModel2>
    fun getNotConfirmAlertSession(): LiveData<AlertSessionModel>
    fun getAlertSessionById(sessionCode: String): AlertSessionModel

    suspend fun updateAlertSession(alertSessionModel: AlertSessionModel)//fun confirmAlertSession(sessionCode: String): Boolean
    suspend fun deleteAllAlertSession()
    suspend fun addAlertSessionRoom(alertSessionModel: AlertSessionModel)
    suspend fun addAlertSessionFB(alertSessionFBModel: AlertSessionFBModel, onSuccess: () -> Unit, onFail: (String) -> Unit)

    fun getDataFB()
}