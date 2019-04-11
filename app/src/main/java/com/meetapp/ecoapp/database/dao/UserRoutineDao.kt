package com.meetapp.ecoapp.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.meetapp.ecoapp.database.entities.UserRoutine

interface UserRoutineDao {
    @Insert
    fun saveUserRoutine(routine: UserRoutine)

    @Delete
    fun deleteUserRoutine(routine: UserRoutine)

    @Query("SELECT * FROM user_routines ORDER BY id")
    fun getAllUseRoutines(): LiveData<List<UserRoutine>>
}