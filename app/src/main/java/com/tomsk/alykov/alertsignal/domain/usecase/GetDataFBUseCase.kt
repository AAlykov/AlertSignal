package com.tomsk.alykov.alertsignal.domain.usecase

import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class GetDataFBUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute() {
        return alertSessionsRepositoryInterface.getDataFB()
    }
}