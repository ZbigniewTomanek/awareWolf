package com.meetapp.ecoapp.database.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin

interface RoutineResourceJoinDao {
    @Insert
    fun insert(routineResourceJoin: RoutineResourceJoin)

    @Query("SELECT * FROM routines INNER JOIN routine_resource_join ON id=routineId WHERE resourceId=:resId")
    fun getResourcesForRoutine(resId: Int): List<RoutineResourceJoin>

    @Query("SELECT * FROM resources INNER JOIN routine_resource_join ON id=resourceId WHERE resourceId=:routineId")
    fun getRoutinesForResource(routineId: Int): List<RoutineResourceJoin>
}