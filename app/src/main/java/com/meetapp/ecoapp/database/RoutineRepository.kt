package com.meetapp.ecoapp.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.database.dao.FrequencyDao
import com.meetapp.ecoapp.database.dao.ResourceDao
import com.meetapp.ecoapp.database.dao.RoutineDao
import com.meetapp.ecoapp.database.dao.RoutineResourceJoinDao
import com.meetapp.ecoapp.database.entities.Frequency
import com.meetapp.ecoapp.database.entities.Resource
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "RoutineRepository"

class RoutineRepository private constructor(application: Application) {

    private val routineDao: RoutineDao
    private val resourceDao: ResourceDao
    private val rrJoinDao: RoutineResourceJoinDao
    private val frequencyDao: FrequencyDao

    private lateinit var allRoutines: LiveData<List<Routine>>
    private lateinit var allRoutinesList: List<Routine>
    private lateinit var allFrequencies: List<Frequency>
    private lateinit var allResources: List<Resource>
    private lateinit var allrrJoin: List<RoutineResourceJoin>

    companion object {
        private var INSTANCE: RoutineRepository? = null

        fun initInstance(application: Application) {
            if (INSTANCE == null)
                INSTANCE = RoutineRepository(application)
        }

        fun instance(): RoutineRepository = INSTANCE ?: throw Exception()

    }

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        routineDao = database.routineDao()
        resourceDao = database.resourceDao()
        rrJoinDao = database.routineResourceJoinDao()
        frequencyDao = database.frequencyDao()

        runBlocking {
            this.launch(Dispatchers.IO) {
                allRoutines = routineDao.all
                allFrequencies = frequencyDao.all
                allResources = resourceDao.all
                allrrJoin = rrJoinDao.all
                allRoutinesList = routineDao.loadAllAsList()

                //initDatabse(application)
                //base was inited only once to load data without involving ui
            }
        }
    }

    fun initDatabse(app: Application) = runBlocking {
        Log.d(TAG, "Starting databse")
        Log.d(TAG, allFrequencies.toString())

        if (allFrequencies.isEmpty()) {
            Log.d(TAG, "Adding freqs")

            val frequencies = listOf(
                Frequency(app.getString(R.string.day), 1),
                Frequency(app.getString(R.string.week), 7), Frequency(app.getString(R.string.month), 30)
            )

            addFrequencies(frequencies)

        }

        if (allResources.isEmpty()) {
            Log.d(TAG, "Adding resources")

            val resources = listOf(
                Resource(app.getString(R.string.water)), Resource(app.getString(R.string.gas), 150),
                Resource(app.getString(R.string.plastic), 200), Resource(app.getString(R.string.fuel), 250)
            )

            addResources(resources)
        }

        if (allRoutinesList.isEmpty()) {
            Log.d(TAG, "Adding routines")

            Log.d(TAG, allFrequencies.map { it.freqId.toString() }.toString())
            Log.d(TAG, allResources.map { it.resourceId.toString() }.toString())

            val routines = listOf(
                Routine(null, app.getString(R.string.routine_teeth_brush), 1, 2.0),
                Routine(null, app.getString(R.string.routine_bicycling_to_work), 1, 1.5),
                Routine(null, app.getString(R.string.routine_no_plastic_bags), 2, 5.0)
            )

            addRoutines(routines)
        }

        Log.d(TAG, allRoutinesList.map { it.routineId }.toString())
        if (allrrJoin.isEmpty()) {
            Log.d(TAG, "Adding resources")

            val routineResourceJoin = listOf(
                RoutineResourceJoin(41, 1),
                RoutineResourceJoin(42, 4),
                RoutineResourceJoin(43, 3)
            )

            addRoutineResourceJoins(routineResourceJoin)
        }
    }

    fun addRoutine(routine: Routine) = runBlocking {
        this.launch(Dispatchers.IO) {
            routineDao.insert(routine)
        }
    }

    fun addRoutines(routines: List<Routine>) = runBlocking {
        this.launch(Dispatchers.IO) {
            routineDao.insertAll(routines)
        }
    }

    fun updateRoutine(routine: Routine) = runBlocking {
        this.launch(Dispatchers.IO) {
            routineDao.update(routine)
        }
    }

    fun getAllRoutinesAsList() = allRoutinesList

    fun deleteRoutine(routine: Routine) = runBlocking {
        this.launch(Dispatchers.IO) {
            routineDao.delete(routine)
        }
    }

    fun addFrequency(frequency: Frequency) = runBlocking {
        this.launch(Dispatchers.IO) {
            frequencyDao.insert(frequency)
        }
    }

    fun addFrequencies(frequencies: List<Frequency>) = runBlocking {
        this.launch(Dispatchers.IO) {
            frequencyDao.insertAll(frequencies)
        }
    }

    fun addRoutineResourceJoin(rrJoin: RoutineResourceJoin) = runBlocking {
        this.launch(Dispatchers.IO) {
            rrJoinDao.insert(rrJoin)
        }
    }

    fun addRoutineResourceJoins(rrJoins: List<RoutineResourceJoin>) = runBlocking {
        this.launch(Dispatchers.IO) {
            rrJoinDao.insertAll(rrJoins)
        }
    }

    fun addResource(resource: Resource) = runBlocking {
        this.launch(Dispatchers.IO) {
            resourceDao.insert(resource)
        }
    }

    fun addResources(resources: List<Resource>) = runBlocking {
        this.launch(Dispatchers.IO) {
            resourceDao.insertAll(resources)
        }
    }

    fun getResourcesNames(routineId: Int): List<String> {
        val ids = allrrJoin.filter { it.routineId == routineId }.map { it.resourceId }
        return allResources.map { if (it.resourceId in ids) it.resourceName else "" }.filter { it != "" }
    }

    fun getFrequencyName(freqId: Int): String = allFrequencies.filter { it.freqId == freqId }.map { it.name }[0] ?: ""


    fun getAllRoutinesList() = allRoutines
    fun getAllResources() = allResources
    fun getAllFrequenciesList() = allFrequencies
    fun getAllrrJoins() = allrrJoin
}