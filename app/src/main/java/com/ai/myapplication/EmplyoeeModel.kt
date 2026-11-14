package com.ai.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Emplyoee")
data class EmplyoeeModel(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val dept : String
)
