package com.meetapp.ecoapp.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "user_routines",
    foreignKeys =
    [
        ForeignKey(
        entity = Routine::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("routineId"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
class UserRoutine constructor(routineId: Int) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userRoutineId: Int = 0

    @ColumnInfo(name = "routine")
    var routineId: Int = routineId
}