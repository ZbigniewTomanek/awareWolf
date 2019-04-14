package com.meetapp.ecoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin

@Dao
interface RoutineResourceJoinDao {
    @get:Query("SELECT * FROM routine_resource_join")
    val all: List<RoutineResourceJoin>

    @Insert
    fun insert(routineResourceJoin: RoutineResourceJoin)

    @Insert
    fun insertAll(routineResourceJoinList: List<RoutineResourceJoin>)

    @Query("SELECT * FROM routines INNER JOIN routine_resource_join ON id=routineId WHERE resourceId=:resId")
    fun getResourcesForRoutine(resId: Int): List<RoutineResourceJoin>

    @Query("SELECT * FROM resources INNER JOIN routine_resource_join ON id=resourceId WHERE resourceId=:routineId")
    fun getRoutinesForResource(routineId: Int): List<RoutineResourceJoin>
}