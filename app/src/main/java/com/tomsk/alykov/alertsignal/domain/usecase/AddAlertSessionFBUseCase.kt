package com.tomsk.alykov.alertsignal.domain.usecase

import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface

class AddAlertSessionFBUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    suspend fun execute(alertSessionFBModel: AlertSessionFBModel, onSuccess:() -> Unit, onFail: (String) -> Unit) {
        alertSessionsRepositoryInterface.addAlertSessionFB(
            alertSessionFBModel,
            {
                onSuccess()
            },
            {
                onFail(it)
            })
    }

}