package com.meetapp.ecoapp.ui.routines

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meetapp.ecoapp.database.RoutineRepository

class RoutineViewModelFactory(val repo: RoutineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoutineViewModel(repo) as T
    }
}