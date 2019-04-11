package com.meetapp.ecoapp.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "resources")
class Resource constructor(name: String, co2Impact_kg: Int = 0){

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var resourceId: Int = 0

    @ColumnInfo(name = "name")
    var resourceName: String = name

    @ColumnInfo(name = "co2impact")
    var co2impact_kg = co2Impact_kg

}