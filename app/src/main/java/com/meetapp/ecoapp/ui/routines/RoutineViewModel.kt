package com.meetapp.ecoapp.ui.routines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.meetapp.ecoapp.database.AppDatabase
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Routine
import org.jetbrains.anko.doAsync

class RoutineViewModel(val repository: RoutineRepository) : ViewModel() {
    fun saveRoutine(routine: Routine) {
        repository.addRoutine(routine)
    }

    fun deleteRoutine(routine: Routine) {
        repository.deleteRoutine(routine)
    }

    fun updateRoutine(routine: Routine) {
        repository.updateRoutine(routine)
    }

    fun getAllRoutinesList() = repository.getAllRoutinesList()

}