package com.meetapp.ecoapp.dagger

import com.meetapp.ecoapp.ui.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(WikiModule::class)])
interface PresenterInjector {

    fun inject(mainPresenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector
        fun wikiModule(wikiModule: WikiModule): Builder
    }
}