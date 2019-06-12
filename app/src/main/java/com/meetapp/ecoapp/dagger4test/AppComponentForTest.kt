package com.meetapp.ecoapp.dagger4test

import android.app.Application
import com.meetapp.ecoapp.dagger.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModuleForTest::class,
        ActivitiesBindingModuleForTest::class
    ]
)

interface AppComponentForTest: AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        fun appModuleForTest(appModuleForTest: AppModuleForTest): Builder

        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponentForTest
    }
}