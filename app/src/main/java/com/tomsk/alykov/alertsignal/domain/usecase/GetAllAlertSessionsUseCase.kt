package com.tomsk.alykov.alertsignal.domain

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class GetAllAlertSessionsUseCase (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(): LiveData<List<AlertSessionModel>> {
        return alertSessionsRepositoryInterface.getAllAlertSessions()
    }
}

class SetTestUseCase (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    suspend fun execute(alertSessionModel: AlertSessionModel) {
        alertSessionsRepositoryInterface.setTest(alertSessionModel)
    }
}

class InsertUseCase (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {

    suspend fun execute(alertSessionModel: AlertSessionModel, onSuccess:() -> Unit) {
        alertSessionsRepositoryInterface.insertTest(alertSessionModel) {
            onSuccess()
        }
    }
}