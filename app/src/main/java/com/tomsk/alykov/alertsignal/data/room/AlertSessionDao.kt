package com.tomsk.alykov.alertsignal.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tomsk.alykov.alertsignal.data.models.AlertSessionRequestAnswerModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal

@Dao
interface AlertSessionDao {

    @Query("select * from alert_sessions_table order by id desc")
    fun getAllAlertSessions(): LiveData<List<AlertSessionModel>>

    @Query("select * from alert_sessions_table where session_confirm_time = '' order by session_start_time desc limit 1")
    fun getNotConfirmAlertSession(): LiveData<AlertSessionModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlertSession(alertSessionModel: AlertSessionModel)

    @Delete
    suspend fun deleteAlertSession(alertSessionModel: AlertSessionModel)

    @Query("delete from alert_sessions_table")
    suspend fun deleteAllAlertSession()

    @Update
    suspend fun updateAlertSession(alertSessionModel: AlertSessionModel)

    @Query("select id from alert_sessions_table where session_code = :sessionCode order by session_start_time desc limit 1")
    fun checkAlertSession(sessionCode: String): Int

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //suspend fun updateAlertSessionCheck(alertSessionCheckModel: AlertSessionCheckModel)

    @Query("update alert_session_request_answer_table set request_time_unix = :requestTimeUnix, request_time = :requestTime, answer_time_unix = '', answer_time = ''")
    suspend fun updateAlertSessionRequest(requestTimeUnix: String, requestTime: String)

    @Query("update alert_session_request_answer_table set answer_time_unix = :answerTimeUnix, answer_time = :answerTime")
    suspend fun updateAlertSessionAnswer(answerTimeUnix: String, answerTime: String)

    @Query("update alert_session_request_answer_table set error_check = :errorCheck")
    suspend fun updateAlertSessionRequestAnswerError(errorCheck: String)

    @Query("select * from alert_session_request_answer_table")
    fun getAlertSessionRequestAnswer(): LiveData<AlertSessionRequestAnswerModel>

    @Query("select * from alert_session_request_answer_table limit 1")
    fun getAlertSessionRequestAnswerModel(): AlertSessionRequestAnswerModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlertSignalSystemJournal(alertSignalSystemJournal: AlertSignalSystemJournal)

    @Query("select * from alert_signal_system_journal order by id desc")
    fun getAllSystemJournal(): LiveData<List<AlertSignalSystemJournal>>

    @Query("select * from alert_signal_system_journal where time_unix > :timeUnix order by id desc")
    fun getTodaySystemJournal(timeUnix: String): LiveData<List<AlertSignalSystemJournal>>
}