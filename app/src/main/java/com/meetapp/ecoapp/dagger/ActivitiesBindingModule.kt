package com.meetapp.ecoapp.dagger

import com.meetapp.ecoapp.ui.main.MainActivity
import com.meetapp.ecoapp.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}