package com.tomsk.alykov.alertsignal.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomsk.alykov.alertsignal.data.AlertSessionsRepositoryInterfaceImpl
import com.tomsk.alykov.alertsignal.domain.GetAllAlertSessionsUseCase
import com.tomsk.alykov.alertsignal.domain.usecase.GetNotConfirmAlertSessionUseCase
import com.tomsk.alykov.alertsignal.domain.SetTestUseCase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertSessionViewModel(application: Application): AndroidViewModel(application) {

    private val alertSessionsRepositoryInterfaceImpl = AlertSessionsRepositoryInterfaceImpl(application)
    private val getAllAlertSessionsUseCase = GetAllAlertSessionsUseCase(alertSessionsRepositoryInterfaceImpl)
    private val getNotConfirmAlertSessionUseCase = GetNotConfirmAlertSessionUseCase(alertSessionsRepositoryInterfaceImpl)
    private val setTest = SetTestUseCase(alertSessionsRepositoryInterfaceImpl)

    val alertSessionsList = getAllAlertSessionsUseCase.execute()
    val notConfirmAlertSession = getNotConfirmAlertSessionUseCase.execute()


    //private val alertSessionRepository: AlertSessionRepository
    //val getAllAlertSessions: LiveData<List<AlertSessionModel>>

    init {
        //val alertSessionDao = AlertSessionDatabase.getDatabase(application).alertSessionDao()
        //alertSessionRepository = AlertSessionRepository(alertSessionDao)
        //getAllAlertSessions = alertSessionRepository.getAllAlertSessions
    }

    fun addAlertSession(alertSessionModel: AlertSessionModel) {
        viewModelScope.launch ( Dispatchers.IO ) {
            //alertSessionRepository.addAlertSession(alertSessionModel)
            setTest.execute(alertSessionModel)
        }
    }


}