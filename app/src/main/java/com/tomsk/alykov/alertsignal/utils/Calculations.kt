package com.tomsk.alykov.alertsignal.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Calculations {

    fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date = sdf.format(Date(stamp.time))
        return date.toString()
    }

    fun timeStampToStringSSS(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS")
        val date = sdf.format(Date(stamp.time))
        return date.toString()
    }
}