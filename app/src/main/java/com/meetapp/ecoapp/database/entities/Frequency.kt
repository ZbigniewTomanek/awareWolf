package com.meetapp.ecoapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "frequencies")
class Frequency constructor(name: String, time_days: Int) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var freqId: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = name

    @ColumnInfo(name = "frequency")
    var time_days: Int? = time_days
}