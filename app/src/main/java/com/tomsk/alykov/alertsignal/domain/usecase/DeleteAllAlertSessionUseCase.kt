package com.tomsk.alykov.alertsignal.domain.usecase

import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class DeleteAllAlertSessionUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    suspend fun execute() {
        alertSessionsRepositoryInterface.deleteAllAlertSession()
    }

}