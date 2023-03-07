package com.tomsk.alykov.alertsignal.domain

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class GetAllAlertSessionsUseCase (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(): LiveData<List<AlertSessionModel>> {
        return alertSessionsRepositoryInterface.getAllAlertSessions()
    }
}
