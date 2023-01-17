package com.tomsk.alykov.alertsignal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_session_table")
data class AlertSessionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name ="session_code")
    val sessionCode: String,

    @ColumnInfo(name ="signal_name")
    val signalName: String,

    @ColumnInfo(name ="signal_type")
    val signalType: Int,

    @ColumnInfo(name ="signal_grade")
    val signalGrade: Int,

    @ColumnInfo(name ="signal_text")
    val signalText: String,

    @ColumnInfo(name ="session_start_time")
    val sessionStartTime: String,

    @ColumnInfo(name ="session_get_time")
    val sessionGetTime: String,

    @ColumnInfo(name ="session_confirm_time")
    val sessionConfirmTime: String,

    @ColumnInfo(name ="user_name")
    val userName: String = "",

    @ColumnInfo(name ="user_id_firebase")
    val userIdFireBase: String = "",

    @ColumnInfo(name ="session_id_firebase")
    val sessionIdFireBase: String = ""
)