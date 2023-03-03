package com.tomsk.alykov.alertsignal.domain.usecase

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class GetNotConfirmAlertSessionUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {

    fun execute(): LiveData<AlertSessionModel> {
        return alertSessionsRepositoryInterface.getNotConfirmAlertSession()
    }
}