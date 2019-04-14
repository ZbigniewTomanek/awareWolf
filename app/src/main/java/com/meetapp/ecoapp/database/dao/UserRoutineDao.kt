package com.meetapp.ecoapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.meetapp.ecoapp.database.entities.UserRoutine

@Dao
interface UserRoutineDao {
    @get:Query("SELECT * FROM user_routines")
    val all: List<UserRoutine>

    @Insert
    fun saveUserRoutine(routine: UserRoutine)

    @Delete
    fun deleteUserRoutine(routine: UserRoutine)

    @Query("SELECT * FROM user_routines ORDER BY id")
    fun getAllUseRoutines(): LiveData<List<UserRoutine>>
}