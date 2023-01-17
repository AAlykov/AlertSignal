package com.tomsk.alykov.alertsignal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tomsk.alykov.alertsignal.database.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.database.room.AlertSessionRepository
import com.tomsk.alykov.alertsignal.models.AlertSessionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertSessionViewModel(application: Application): AndroidViewModel(application) {

    private val alertSessionRepository: AlertSessionRepository
    val getAllAlertSessions: LiveData<List<AlertSessionModel>>

    init {
        val alertSessionDao = AlertSessionDatabase.getDatabase(application).alertSessionDao()
        alertSessionRepository = AlertSessionRepository(alertSessionDao)
        getAllAlertSessions = alertSessionRepository.getAllAlertSessions
    }

    fun addAlertSession(alertSessionModel: AlertSessionModel) {
        viewModelScope.launch ( Dispatchers.IO ) {
            alertSessionRepository.addAlertSession(alertSessionModel)
        }
    }


}