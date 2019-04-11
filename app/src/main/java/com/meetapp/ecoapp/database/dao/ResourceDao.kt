package com.meetapp.ecoapp.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.meetapp.ecoapp.database.entities.Resource

@Dao
interface ResourceDao {
    @get:Query("SELECT * FROM  resources")
    val all: List<Resource>

    @Query("SELECT * FROM resources WHERE id IN (:resourceIds)")
    fun loadAllByIds(resourceIds: Array<Int>): List<Resource>

    @Insert
    fun insertAll(resources: List<Resource>)

    @Insert
    fun insert(frequency: Resource)

    @Delete
    fun delete(frequency: Resource)
}