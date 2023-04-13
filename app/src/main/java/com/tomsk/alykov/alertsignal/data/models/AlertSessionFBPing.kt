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


data class AlertSessionFBPing(
    val myId: String = "",
    val pingTimeUnix: String = "",
    val pingTime: String = "",
    val info: String = ""
): Serializable
