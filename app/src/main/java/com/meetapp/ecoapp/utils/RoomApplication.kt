package com.meetapp.ecoapp.utils

import android.app.Application
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.database.AppDatabase
import com.meetapp.ecoapp.database.entities.Frequency
import com.meetapp.ecoapp.database.entities.Resource
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin
import org.jetbrains.anko.doAsync

class RoomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        prefillDatabase()

        }

    private fun prefillDatabase() {
        doAsync {
            val database = AppDatabase.getInstance(this@RoomApplication)

            if (database.frequencyDao().all.isEmpty()) {
                val frequencies = listOf(Frequency(getString(R.string.day), 1),
                    Frequency(getString(R.string.week), 7), Frequency(getString(R.string.month), 30))

                database.frequencyDao().insertAll(frequencies)
            }

            if (database.resourceDao().all.isEmpty()) {
                val resources = listOf(Resource(getString(R.string.water)), Resource(getString(R.string.gas), 150),
                    Resource(getString(R.string.plastic), 200), Resource(getString(R.string.fuel), 250))

                database.resourceDao().insertAll(resources)
            }

            if (database.routineDao().all.isEmpty()) {
                val routines = listOf(
                    Routine(getString(R.string.routine_teeth_brush), 0, 2.0),
                    Routine(getString(R.string.routine_bicycling_to_work), 0, 1.5),
                    Routine(getString(R.string.routine_no_plastic_bags), 1, 5.0)
                )

                database.routineDao().insertAll(routines)
            }

            if (database.routineResourceJoinDao().all.isEmpty()) {
                val routineResourceJoin = listOf(RoutineResourceJoin(0, 0),
                    RoutineResourceJoin(1, 3),
                    RoutineResourceJoin(2, 2))

                database.routineResourceJoinDao().insertAll(routineResourceJoin)
            }
    }
}
}