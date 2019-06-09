package com.meetapp.ecoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.ui.routines.RoutineViewModel
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
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
        Mockito.`when`(repository.getAllRoutinesList()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Routine>())
        }

        //assertNotNull(viewModel.getAllRoutinesList().value)
    }
}