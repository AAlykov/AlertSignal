package com.tomsk.alykov.alertsignal.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//session_code
//sender_name
//signal_name
//signal_type
//signal_grade
//signal_text
//session_start_time_unix
//session_start_time
//session_get_time_unix
//session_get_time
//session_confirm_time_unix
//session_confirm_time
//user_name
//user_id_firebase
//session_id_firebase

@Entity(tableName = "alert_sessions_table")
data class AlertSessionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name ="session_code")
    val sessionCode: String,

    @ColumnInfo(name ="sender_name")
    val senderName: String,

    @ColumnInfo(name ="signal_name")
    val signalName: String,

    @ColumnInfo(name ="signal_type")
    val signalType: Int,

    @ColumnInfo(name ="signal_grade")
    val signalGrade: Int,

    @ColumnInfo(name ="signal_text")
    val signalText: String,

    @ColumnInfo(name ="session_start_time_unix")
    val sessionStartTimeUnix: String,
    @ColumnInfo(name ="session_start_time")
    val sessionStartTime: String,

    @ColumnInfo(name ="session_get_time_unix")
    val sessionGetTimeUnix: String,
    @ColumnInfo(name ="session_get_time")
    val sessionGetTime: String,

    @ColumnInfo(name ="session_confirm_time_unix")
    val sessionConfirmTimeUnix: String,
    @ColumnInfo(name ="session_confirm_time")
    val sessionConfirmTime: String,

    @ColumnInfo(name ="user_name")
    val userName: String = "",

    @ColumnInfo(name ="user_id_firebase")
    val userIdFireBase: String = "",

    @ColumnInfo(name ="session_id_firebase")
    val sessionIdFireBase: String = ""
): Serializable