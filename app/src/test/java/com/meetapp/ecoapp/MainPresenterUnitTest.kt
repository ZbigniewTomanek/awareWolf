package com.meetapp.ecoapp

import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.network.WikiService
import com.meetapp.ecoapp.ui.main.*
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@SmallTest
@RunWith(AndroidJUnit4::class)
class MainPresenterUnitTest {

    @Rule
    @JvmField
    var rule = daggerMockRule()

    lateinit var presenter: MainPresenter

    @Mock
    lateinit var mainView: MainView

    @Mock
    lateinit var wikiService: WikiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mainView = Mockito.spy(mainView)

        presenter = MainPresenter(wikiService)
        presenter = Mockito.spy(presenter)

        presenter.attach(mainView)
    }

    @Test
    fun searchDefinition_positiveResult() {
        Mockito.`when`(wikiService
            .loadDefinitions(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenAnswer {
                mainView.buildInfoDialog(listOf())
            return@thenAnswer Observable.just(listOf<WikiModel.Element>())
        }

        val query = "kot"
        presenter.searchDefinition(query)

        Mockito.verify(mainView, Mockito.times(1))
            .buildInfoDialog(ArgumentMatchers.anyList<WikiModel.Element>())
    }

    @Test
    fun searchDefinition_negativeResult() {
        val query = ""
        presenter.searchDefinition(query)

        Mockito.verify(mainView, Mockito.times(1)).showErrorToast(ArgumentMatchers.anyString())
    }

}