package com.tomsk.alykov.alertsignal.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomsk.alykov.alertsignal.data.AlertSessionsRepositoryInterfaceImpl
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.domain.GetAllAlertSessionsUseCase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertSessionViewModel(application: Application): AndroidViewModel(application) {

    private val alertSessionsRepositoryInterfaceImpl = AlertSessionsRepositoryInterfaceImpl(application)

    private val getAllAlertSessionsUseCase = GetAllAlertSessionsUseCase(alertSessionsRepositoryInterfaceImpl)
    private val getNotConfirmAlertSessionUseCase = GetNotConfirmAlertSessionUseCase(alertSessionsRepositoryInterfaceImpl)

    private val getAlertSessionCheckUseCase = GetAlertSessionCheckUseCase(alertSessionsRepositoryInterfaceImpl)

    private val getTodayAlertSignalJournalUseCase = GetTodayAlertSignalJournalUseCase(alertSessionsRepositoryInterfaceImpl)

    private val confirmAlertSessionUseCase = ConfirmAlertSessionUseCase(alertSessionsRepositoryInterfaceImpl)
    private val deleteAllRoomUseCase = DeleteAllAlertSessionUseCase(alertSessionsRepositoryInterfaceImpl)

    private val addAlertSessionRoomUseCase = AddAlertSessionRoomUseCase(alertSessionsRepositoryInterfaceImpl)

    private val addAlertSessionFBUseCase = AddAlertSessionFBUseCase(alertSessionsRepositoryInterfaceImpl)

    val alertSessionsList = getAllAlertSessionsUseCase.execute()
    val notConfirmAlertSession = getNotConfirmAlertSessionUseCase.execute()
    val alertSessionCheck = getAlertSessionCheckUseCase.execute()

    var nowTimeUnix = System.currentTimeMillis() - 1000 * 60 * 60 * 24
    val getTodaySystemJournal = getTodayAlertSignalJournalUseCase.execute(nowTimeUnix.toString())

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
            addAlertSessionRoomUseCase.execute(alertSessionModel)
        }
    }

    fun confirmAlertSession(alertSessionModel: AlertSessionModel) {
        viewModelScope.launch ( Dispatchers.IO ) {
            confirmAlertSessionUseCase.execute(alertSessionModel)
        }
    }

    fun deleteAllRoomUseCase() {
        viewModelScope.launch ( Dispatchers.IO ) {
            deleteAllRoomUseCase.execute()
        }

    }


    fun addAlertSessionFB(alertSessionFBModel: AlertSessionFBModel, onSuccess:()->Unit, onFail: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            addAlertSessionFBUseCase.execute(
                alertSessionFBModel,
                {
                    onSuccess()
                },
                {
                    onFail(it)
                })
        }
    }
}