package com.tomsk.alykov.alertsignal.domain.usecase

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel2
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class GetAlertSessionCheckUseCase (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(): LiveData<AlertSessionCheckModel> {
        return alertSessionsRepositoryInterface.getAlertSessionCheck()
    }
}

class GetAlertSessionCheckUseCase2 (private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(): LiveData<AlertSessionCheckModel2> {
        return alertSessionsRepositoryInterface.getAlertSessionCheck2()
    }
}