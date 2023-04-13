package com.tomsk.alykov.alertsignal.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "alert_session_request_answer_table")
data class AlertSessionRequestAnswerModel(

    @PrimaryKey() val id: Int = 0,

    @ColumnInfo(name ="request_time_unix")
    val requestTimeUnix: String = "",

    @ColumnInfo(name ="request_time")
    val requestTime: String = "",

    @ColumnInfo(name ="answer_time_unix")
    val answerTimeUnix: String = "",

    @ColumnInfo(name ="answer_time")
    val answerTime: String = "",

    @ColumnInfo(name ="error_check")
    val errorCheck: String = "",

    @ColumnInfo(name ="session_id_firebase")
    val sessionIdFireBase: String = ""
): Serializable