package com.tomsk.alykov.alertsignal.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

@Database(entities = [AlertSessionModel::class], version = 1)
abstract class AlertSessionDatabase: RoomDatabase() {
    abstract fun alertSessionDao(): AlertSessionDao
    companion object {
        @Volatile //= как только мы получим к нему (экземпляру) доступ он станет виден для всех потоков
        private var INSTANCE: AlertSessionDatabase? =null //Создаем экзмпляр БД

        fun getDatabase(context: Context): AlertSessionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlertSessionDatabase::class.java,
                    "alert_session_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}