package com.meetapp.ecoapp.ui.routines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.meetapp.ecoapp.database.AppDatabase
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Routine
import org.jetbrains.anko.doAsync

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    val repository = RoutineRepository.instance()
    private val routinesList: LiveData<List<Routine>> = repository.getAllRoutinesList()

    fun saveRoutine(routine: Routine) {
        repository.addRoutine(routine)
    }

    fun deleteRoutine(routine: Routine) {
        repository.deleteRoutine(routine)
    }

    fun updateRoutine(routine: Routine) {
        repository.updateRoutine(routine)
    }

    fun getAllRoutinesList() = routinesList

}