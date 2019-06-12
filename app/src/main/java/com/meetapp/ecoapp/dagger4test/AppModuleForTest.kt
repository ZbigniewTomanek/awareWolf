package com.meetapp.ecoapp.dagger4test


import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.meetapp.ecoapp.network.WikiService
import com.meetapp.ecoapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModuleForTest {

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideHTTpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()


    @Provides
    @Singleton
    fun provideRetrofitInterface(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://pl.wikipedia.org/w/")
        .build()

    @Provides
    fun provideWikiService(): WikiService = Mockito.mock(WikiService::class.java)

    @Provides
    fun provideMainPresenter(wikiService: WikiService): MainPresenter = MainPresenter(wikiService)
}