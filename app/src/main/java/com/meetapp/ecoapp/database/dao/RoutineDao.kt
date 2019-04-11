package com.meetapp.ecoapp.database.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.meetapp.ecoapp.database.entities.Routine

interface RoutineDao {
    @get:Query("SELECT * FROM routines")
    val all: List<Routine>

    @Query("SELECT * FROM routines WHERE id IN (:routinesIds)")
    fun loadAllByIds(routinesIds: Array<Int>): List<Routine>

    @Query("SELECT * FROM routines WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Routine

    @Insert
    fun insertAll(routines: List<Routine>)

    @Insert
    fun insert(routine: Routine)

    @Delete
    fun delete(routine: Routine)
}