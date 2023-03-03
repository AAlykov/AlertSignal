package com.tomsk.alykov.alertsignal.domain.usecase

import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class GetAlertSessionByIdUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(sessionCode: String): AlertSessionModel {
        return alertSessionsRepositoryInterface.getAlertSessionById(sessionCode = sessionCode)
    }
}