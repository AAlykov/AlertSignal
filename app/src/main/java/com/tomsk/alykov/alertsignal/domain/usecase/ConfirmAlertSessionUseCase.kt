package com.tomsk.alykov.alertsignal.domain.usecase

import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class ConfirmAlertSessionUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    suspend fun execute(alertSessionModel: AlertSessionModel) {
        alertSessionsRepositoryInterface.updateAlertSession(alertSessionModel)
    }
}