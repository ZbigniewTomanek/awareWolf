package com.meetapp.ecoapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.meetapp.ecoapp.database.entities.Routine

@Dao
interface RoutineDao {
    @get:Query("SELECT * FROM routines")
    val all: LiveData<List<Routine>>

    @Query("SELECT * FROM routines WHERE id IN (:routinesIds)")
    fun loadAllByIds(routinesIds: Array<Int>): LiveData<List<Routine>>

    @Query("SELECT * FROM routines")
    fun loadAllAsList(): List<Routine>

    @Query("SELECT * FROM routines WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Routine

    @Query("DELETE FROM routines")
    fun nukeTable()

    @Insert
    fun insertAll(routines: List<Routine>)

    @Insert
    fun insert(routine: Routine)

    @Delete
    fun delete(routine: Routine)
}