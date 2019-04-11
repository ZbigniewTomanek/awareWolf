package com.meetapp.ecoapp.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "routines", foreignKeys =
            [
             ForeignKey(
                 entity = Frequency::class,
                 parentColumns = arrayOf("id"),
                 childColumns = arrayOf("freqId"),
                 onDelete = ForeignKey.NO_ACTION
             )])
class Routine constructor(name: String, freqId: Int) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var routineId: Int = 0

    @ColumnInfo(name = "name")
    var routineName: String = name

    @ColumnInfo(name = "frequencyId")
    var freqId: Int = freqId

}