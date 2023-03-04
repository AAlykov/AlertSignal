package com.tomsk.alykov.alertsignal.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tomsk.alykov.alertsignal.data.models.AlertSessionCheckModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

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

    @Query("select * from alert_sessions_table where session_code = :sessionCode order by session_start_time desc limit 1")
    fun checkAlertSession(sessionCode: String): AlertSessionModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlertSessionCheck(alertSessionCheckModel: AlertSessionCheckModel)

    @Query("select * from alert_sessions_check_table")
    fun getAlertSessionCheck(): LiveData<AlertSessionCheckModel>

}