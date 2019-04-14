package com.meetapp.ecoapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "routine_resource_join",
        primaryKeys = ["routineId", "resourceId"],
    foreignKeys =
    [
        ForeignKey(
            entity = Routine::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("routineId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Resource::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("resourceId"),
            onDelete = ForeignKey.CASCADE
        )]
    )

class RoutineResourceJoin(routineId: Int, resourceId: Int) {

    var routineId: Int = routineId
    var resourceId: Int = resourceId

}