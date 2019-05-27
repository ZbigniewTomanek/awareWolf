package com.meetapp.ecoapp.dagger

import com.meetapp.ecoapp.ui.main.MainPresenter

abstract class BasePresenterInjector  {
    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .wikiModule(WikiModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainPresenter -> injector.inject(this)
        }
    }
}