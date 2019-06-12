package com.meetapp.ecoapp.ui.main

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.meetapp.ecoapp.network.WikiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pl.wikipedia.org/w/"

@Module
class MainActivityModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideHTTpClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        @JvmStatic
        @Provides
        fun provideRetrofitInterface(client: OkHttpClient): Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        @JvmStatic
        @Provides
        fun provideWikiService(retrofit: Retrofit): WikiService {
            return retrofit.create(WikiService::class.java)
        }

        @JvmStatic
        @Provides
        fun provideMainPresenter(wikiService: WikiService): MainPresenter = MainPresenter(wikiService)
    }
}