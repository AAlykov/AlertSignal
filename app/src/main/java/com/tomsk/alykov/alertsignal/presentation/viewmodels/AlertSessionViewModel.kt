package com.tomsk.alykov.alertsignal.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomsk.alykov.alertsignal.data.AlertSessionsRepositoryInterfaceImpl
import com.tomsk.alykov.alertsignal.domain.GetAllAlertSessionsUseCase
import com.tomsk.alykov.alertsignal.domain.InsertUseCase
import com.tomsk.alykov.alertsignal.domain.usecase.GetNotConfirmAlertSessionUseCase
import com.tomsk.alykov.alertsignal.domain.SetTestUseCase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.usecase.GetAlertSessionCheckUseCase
import com.tomsk.alykov.alertsignal.domain.usecase.GetDataFBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertSessionViewModel(application: Application): AndroidViewModel(application) {

    private val alertSessionsRepositoryInterfaceImpl = AlertSessionsRepositoryInterfaceImpl(application)
    private val getAllAlertSessionsUseCase = GetAllAlertSessionsUseCase(alertSessionsRepositoryInterfaceImpl)
    private val getNotConfirmAlertSessionUseCase = GetNotConfirmAlertSessionUseCase(alertSessionsRepositoryInterfaceImpl)
    private val getAlertSessionCheckUseCase = GetAlertSessionCheckUseCase(alertSessionsRepositoryInterfaceImpl)

    private val setTest = SetTestUseCase(alertSessionsRepositoryInterfaceImpl)
    private val insert = InsertUseCase(alertSessionsRepositoryInterfaceImpl)

    val alertSessionsList = getAllAlertSessionsUseCase.execute()
    val notConfirmAlertSession = getNotConfirmAlertSessionUseCase.execute()
    val alertSessionCheck = getAlertSessionCheckUseCase.execute()


    //private val getDataFBUseCase = GetDataFBUseCase(alertSessionsRepositoryInterfaceImpl)
    private val getDataFBUseCase by lazy { GetDataFBUseCase(alertSessionsRepositoryInterfaceImpl) }


    //private val alertSessionRepository: AlertSessionRepository
    //val getAllAlertSessions: LiveData<List<AlertSessionModel>>

    init {
        getDataFBUseCase.execute()
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

    fun addAlertSession2(alertSessionModel: AlertSessionModel, onSuccess:()->Unit) {
        viewModelScope.launch ( Dispatchers.IO ) {
            //alertSessionRepository.addAlertSession(alertSessionModel)
            insert.execute(alertSessionModel) {
                onSuccess()
            }
        }
    }


}