package com.meetapp.ecoapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "user_routines",
    foreignKeys =
    [
        ForeignKey(
        entity = Routine::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
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