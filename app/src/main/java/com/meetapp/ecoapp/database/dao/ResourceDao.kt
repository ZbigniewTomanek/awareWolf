package com.meetapp.ecoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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