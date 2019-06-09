package com.meetapp.ecoapp

import com.meetapp.ecoapp.network.WikiService
import com.meetapp.ecoapp.ui.main.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import org.junit.Rule
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import javax.inject.Inject


class MainPresenterUnitTest {
    lateinit var presenter: MainPresenter

    @Mock
    lateinit var mainView: MainView
    
    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var wikiService: WikiService

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)

        wikiService
        presenter = Mockito.spy(MainPresenter(mainView))
    }

    @Test
    fun searchDefinition() {
        val query = "wielka lechia"

        presenter.searchDefinition(query)

        Mockito.verify(wikiService, Mockito.times(1)).loadDefinitions(RETROFIT_ACTION, RETROFIT_FORMAT, RETROFIT_SEARCH, query)
    }

}