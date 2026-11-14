package com.ai.myapplication

import kotlinx.coroutines.flow.Flow

class EmplyoeeReprocity(private val emplyoeeDao: EmplyoeeDao) {

    val allEmplyoee: Flow<List<EmplyoeeModel>> = emplyoeeDao.getAllEmplyoee()

    suspend fun insert(emplyoeeModel: EmplyoeeModel) =
        emplyoeeDao.insertEmplyoee(emplyoeeModel)

    suspend fun update(emplyoeeModel: EmplyoeeModel) =
        emplyoeeDao.updateEmplyoee(emplyoeeModel)

    suspend fun delete(emplyoeeModel: EmplyoeeModel) =
        emplyoeeDao.deleteEmplyoee(emplyoeeModel)

    suspend fun getById(id: Int): EmplyoeeModel? =
        emplyoeeDao.getEmplyoeeById(id)

    suspend fun deleteAll() = emplyoeeDao.deleteAllEmplyoee()
}
