package com.ai.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EmplyoeeViewModel(application: Application) : AndroidViewModel(application) {

    private val emplyoeeReprocity: EmplyoeeReprocity

    val allEmplyoee: StateFlow<List<EmplyoeeModel>>

    init {
        val dao = EmplyoeeDatabase.getDatabase(application).emplyoeeDao()
        emplyoeeReprocity = EmplyoeeReprocity(dao)
        allEmplyoee = emplyoeeReprocity.allEmplyoee.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    }

    /** CREATE */
    fun createEmplyoee(name: String, dept: String) = viewModelScope.launch {
        emplyoeeReprocity.insert(EmplyoeeModel(name = name, dept = dept))
    }

    /** UPDATE */
    fun updateEmplyoee(emplyoeeModel: EmplyoeeModel) = viewModelScope.launch {
        emplyoeeReprocity.update(emplyoeeModel)
    }

    /** UPDATE - id vechu name/dept maathanum na */
    fun updateEmplyoee(id: Int, name: String, dept: String) = viewModelScope.launch {
        emplyoeeReprocity.update(EmplyoeeModel(id = id, name = name, dept = dept))
    }

    /** DELETE */
    fun deleteEmplyoee(emplyoeeModel: EmplyoeeModel) = viewModelScope.launch {
        emplyoeeReprocity.delete(emplyoeeModel)
    }

    fun deleteAllEmplyoee() = viewModelScope.launch {
        emplyoeeReprocity.deleteAll()
    }
}
