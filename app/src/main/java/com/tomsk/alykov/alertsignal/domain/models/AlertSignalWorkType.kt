package com.tomsk.alykov.alertsignal.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_sessions_system_journal")
data class AlertSignalWorkType(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name ="work_type")
    val workType: String,

    @ColumnInfo(name ="work_describe")
    val workDescribe: String,
)
