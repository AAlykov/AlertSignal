package com.tomsk.alykov.alertsignal.data

import com.tomsk.alykov.alertsignal.data.room.AlertSessionDao
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Utils {
    fun writeToSystemJournal(alertSessionDao: AlertSessionDao, s: String) {
        val nowTimeUnix = System.currentTimeMillis()
        val nowTime = timeStampToStringSSS(nowTimeUnix)
        val alertSignalSystemJournal =
            AlertSignalSystemJournal(0, nowTimeUnix.toString(), nowTime, s)
        CoroutineScope(Dispatchers.IO).launch {
            alertSessionDao.addAlertSignalSystemJournal(alertSignalSystemJournal)
        }
    }

    fun timeStampToStringSSS(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS")
        val date = sdf.format(Date(stamp.time))
        return date.toString()
    }

    fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date = sdf.format(Date(stamp.time))
        return date.toString()
    }

}