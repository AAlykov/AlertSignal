package com.tomsk.alykov.alertsignal.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

/*var alertSessionFBModel = AlertSessionFBModel("001/1235", "Объект 178", "Техническая проверка системы оповещения",
                    1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст", "1678115277999", "06.03.2023 22:07:57", "")
myRef.setValue(alertSessionFBModel)
                */
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
