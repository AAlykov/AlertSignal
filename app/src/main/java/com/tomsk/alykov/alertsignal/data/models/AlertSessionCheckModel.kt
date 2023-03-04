package com.tomsk.alykov.alertsignal.data.models

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
//session_start_time
//session_check_time_unix
//session_check_time
//error_check_time
//session_id_firebase


@Entity(tableName = "alert_sessions_check_table")
data class AlertSessionCheckModel(

    @PrimaryKey() val id: Int = 0,

    @ColumnInfo(name ="session_code")
    val sessionCode: String = "",

    @ColumnInfo(name ="sender_name")
    val senderName: String = "",

    @ColumnInfo(name ="signal_name")
    val signalName: String = "",

    @ColumnInfo(name ="signal_type")
    val signalType: Int = 0,

    @ColumnInfo(name ="signal_grade")
    val signalGrade: Int = 0,

    @ColumnInfo(name ="signal_text")
    val signalText: String = "",

    @ColumnInfo(name ="session_start_time")
    val sessionStartTime: String = "",

    @ColumnInfo(name ="session_check_time_unix")
    val sessionCheckTimeUnix: String = "",

    @ColumnInfo(name ="session_check_time")
    val sessionCheckTime: String = "",

    @ColumnInfo(name ="error_check")
    val errorCheck: String = "",

    @ColumnInfo(name ="session_id_firebase")
    val sessionIdFireBase: String = ""
): Serializable