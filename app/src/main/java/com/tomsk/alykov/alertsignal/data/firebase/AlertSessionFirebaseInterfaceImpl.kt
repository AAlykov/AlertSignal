package com.tomsk.alykov.alertsignal.data.firebase

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tomsk.alykov.alertsignal.data.room.AlertSessionDatabase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertSessionFirebaseInterfaceImpl(private val application: Application): AlertSessionsFirebaseInterface {

    private val alertSessionDao = AlertSessionDatabase.getDatabase(application).alertSessionDao()

    override fun getAlertSessionFB(): String {

        var res = ""
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        myRef.get().addOnSuccessListener {
            Log.d("AADebug", "getAlertSessionFB: myRef.get().addOnSuccessListener " + it.toString())
            val value = it.getValue<String>()
            res = value.toString()

            Log.d("AADebug", "getAlertSessionFB: Start Coroutine")

            CoroutineScope(Dispatchers.Main).launch {
                Log.d("AADebug", "getAlertSessionFB: CoroutineScope 1")
                val alertSessionModel = AlertSessionModel(0, "001/1234", "Объект 178","Техническая проверка системы оповещения",
                    1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                    "20230117 12:40:01", "20230117 12:40:10", "", "", "", "")
                alertSessionDao.addAlertSession(alertSessionModel)
                Log.d("AADebug", "getAlertSessionFB: CoroutineScope 2")
            }
        }
        

        /*myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                if (value != null)
                    res = value
                Log.d("AADebug", "onDataChange: Value is: " + value)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("AADebug", "onCancelled: Failed to read value.", error.toException())
            }

        })
*/
        return res
    }
}

