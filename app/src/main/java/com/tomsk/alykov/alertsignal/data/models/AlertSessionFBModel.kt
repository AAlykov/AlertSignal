package com.tomsk.alykov.alertsignal.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

//session_code
//sender_name
//signal_name
//signal_type
//signal_grade
//signal_text
//session_start_time
//session_check_time
//error_check_time
//session_id_firebase


data class AlertSessionFBModel(
    val sessionCode: String = "",
    val senderName: String = "",
    val signalName: String = "",
    val signalType: Int = 0,
    val signalGrade: Int = 0,
    val signalText: String = "",
    val sessionStartTimeUnix: String = "",
    val sessionStartTime: String = "",
    val sessionIdFireBase: String = ""
): Serializable
