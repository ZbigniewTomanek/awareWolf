package com.meetapp.ecoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.ui.routines.RoutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RoutineViewModelUnitTest {

    lateinit var viewModel: RoutineViewModel

    @Mock
    lateinit var repository: RoutineRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = RoutineViewModel(repository)
    }

    @Test
    fun loadRoutines_positiveResponse() {
        val mld = MutableLiveData<List<Routine>>()
        mld.postValue(listOf(Routine(1, "", 1, 1.0)))

        Mockito.`when`(repository.getAllRoutinesList()).thenAnswer {
            return@thenAnswer mld
        }

        assertNotNull(viewModel.getAllRoutinesList().value)
    }

    @Test
    fun loadRoutines_negativeResponse() {
        Mockito.`when`(repository.getAllRoutinesList()).thenAnswer {
            return@thenAnswer MutableLiveData<List<Routine>>()
        }

        assertNull(viewModel.getAllRoutinesList().value)
    }

    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    @Test
    fun saveRoutine_positiveResponse() {
        val mld = MutableLiveData<List<Routine>>()
        val sampleRoutine = Routine(1, "", 1, 1.0)

        Mockito.`when`(repository.getAllRoutinesList()).thenAnswer {
            return@thenAnswer mld
        }

        Mockito.`when`(repository.addRoutine(anyObject())).thenAnswer {
            mld.postValue(listOf(sampleRoutine))

            return@thenAnswer runBlocking {
                this.launch(Dispatchers.IO) {

                }
            }
        }

        assertNull(viewModel.getAllRoutinesList().value)

        viewModel.saveRoutine(sampleRoutine)

        assert(viewModel.getAllRoutinesList().value == listOf(sampleRoutine))
    }
}