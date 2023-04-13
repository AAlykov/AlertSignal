package com.tomsk.alykov.alertsignal.domain.usecase

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.data.models.AlertSessionRequestAnswerModel
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface

class GetAlertSessionCheckUseCase (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(): LiveData<AlertSessionRequestAnswerModel> {
        return alertSessionsRepositoryInterface.getAlertSessionRequestAnswer()
    }
}