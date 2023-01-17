package com.tomsk.alykov.alertsignal.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tomsk.alykov.alertsignal.models.AlertSessionModel

@Dao
interface AlertSessionDao {

    @Query("select * from alert_session_table order by id desc")
    fun getAllAlertSessions(): LiveData<List<AlertSessionModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlertSession(alertSessionModel: AlertSessionModel)

    @Delete
    suspend fun deleteAlertSession(alertSessionModel: AlertSessionModel)

    @Query("delete from alert_session_table")
    suspend fun deleteAllAlertSession()

    @Update
    suspend fun updateAlertSession(alertSessionModel: AlertSessionModel)
}