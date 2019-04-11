package com.meetapp.ecoapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.meetapp.ecoapp.database.dao.*
import com.meetapp.ecoapp.database.entities.*

@Database(entities = [(Frequency::class), (Resource::class), (Routine::class), (RoutineResourceJoin::class), (UserRoutine::class)], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun frequencyDao(): FrequencyDao

    abstract fun resourceDao(): ResourceDao

    abstract fun routineDao(): RoutineDao

    abstract fun routineResourceJoin(): RoutineResourceJoinDao

    abstract fun userRoutineDao(): UserRoutineDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(ctx.applicationContext, AppDatabase::class.java, "dsf")
                    .fallbackToDestructiveMigration()
                    .build()

            }

            return INSTANCE!!
        }
    }
}