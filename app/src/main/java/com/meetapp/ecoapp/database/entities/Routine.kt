package com.meetapp.ecoapp.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "routines", foreignKeys =
            [
             ForeignKey(
                 entity = Frequency::class,
                 parentColumns = arrayOf("id"),
                 childColumns = arrayOf("frequencyId"),
                 onDelete = ForeignKey.NO_ACTION
             )])
class Routine constructor(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var routineId: Int?,
                          @ColumnInfo(name = "name") var routineName: String,
                          @ColumnInfo(name = "frequencyId") var freqId: Int,
                          @ColumnInfo(name = "amount") var amountSaved: Double): Parcelable {
}