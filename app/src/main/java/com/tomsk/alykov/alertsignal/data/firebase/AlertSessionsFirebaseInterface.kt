package com.tomsk.alykov.alertsignal.data.firebase

import androidx.lifecycle.LiveData
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

interface AlertSessionsFirebaseInterface {
    fun getAlertSessionFB(): String //AlertSessionModel
}