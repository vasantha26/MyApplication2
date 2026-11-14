package com.ai.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmplyoeeModel::class], version = 1, exportSchema = false)
abstract class EmplyoeeDatabase : RoomDatabase() {

    abstract fun emplyoeeDao(): EmplyoeeDao

    companion object {
        @Volatile
        private var INSTANCE: EmplyoeeDatabase? = null

        fun getDatabase(context: Context): EmplyoeeDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EmplyoeeDatabase::class.java,
                    "emplyoee_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
