package com.meetapp.ecoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.meetapp.ecoapp.database.entities.Frequency


@Dao
interface FrequencyDao {

    @get:Query("SELECT * FROM frequencies")
    val all: List<Frequency>

    @Query("SELECT * FROM frequencies WHERE id IN (:frequencyIds)")
    fun loadAllByIds(frequencyIds: Array<Int>): List<Frequency>

    @Query("SELECT * FROM frequencies WHERE frequency LIKE :len LIMIT 1")
    fun findByLength(len: Int): Frequency

    @Insert
    fun insertAll(frequencies: List<Frequency>)

    @Insert
    fun insert(frequency: Frequency)

    @Delete
    fun delete(frequency: Frequency)
}