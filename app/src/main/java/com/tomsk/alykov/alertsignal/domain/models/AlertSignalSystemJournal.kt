package com.tomsk.alykov.alertsignal.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "alert_signal_system_journal")
data class AlertSignalSystemJournal (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name ="time_unix")
    val timeUnix: String,

    @ColumnInfo(name ="time")
    val time: String,

    @ColumnInfo(name ="action")
    val action: String
): Serializable
