package com.tomsk.alykov.alertsignal.domain.usecase

import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface

class ConfirmAlertSessionUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(sessionCode: String) {
        alertSessionsRepositoryInterface.confirmAlertSession(sessionCode = sessionCode)
    }
}