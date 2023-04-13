package com.tomsk.alykov.alertsignal.domain.usecase

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.domain.AlertSessionsRepositoryInterface
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal

class GetAlertSignalJournalUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(): LiveData<List<AlertSignalSystemJournal>> {
        return alertSessionsRepositoryInterface.getAllSystemJournal()
    }
}

class GetTodayAlertSignalJournalUseCase(private val alertSessionsRepositoryInterface: AlertSessionsRepositoryInterface) {
    fun execute(timeUnix: String): LiveData<List<AlertSignalSystemJournal>> {
        return alertSessionsRepositoryInterface.getTodaySystemJournal(timeUnix)
    }
}