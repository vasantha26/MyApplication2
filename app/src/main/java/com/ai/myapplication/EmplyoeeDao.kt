package com.ai.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmplyoeeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmplyoee(emplyoeeModel: EmplyoeeModel)

    @Update
    suspend fun updateEmplyoee(emplyoeeModel: EmplyoeeModel)

    @Delete
    suspend fun deleteEmplyoee(emplyoeeModel: EmplyoeeModel)

    @Query("SELECT * FROM Emplyoee ORDER BY id ASC")
    fun getAllEmplyoee(): Flow<List<EmplyoeeModel>>

    @Query("SELECT * FROM Emplyoee WHERE id = :id")
    suspend fun getEmplyoeeById(id: Int): EmplyoeeModel?

    @Query("DELETE FROM Emplyoee")
    suspend fun deleteAllEmplyoee()
}
