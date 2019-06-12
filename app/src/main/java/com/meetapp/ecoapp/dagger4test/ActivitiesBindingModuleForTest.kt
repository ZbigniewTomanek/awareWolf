package com.meetapp.ecoapp.dagger4test

import com.meetapp.ecoapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModuleForTest {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}