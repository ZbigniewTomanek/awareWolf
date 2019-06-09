package com.meetapp.ecoapp.ui.main

import com.meetapp.ecoapp.network.WikiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val RETROFIT_ACTION = "query"
const val RETROFIT_FORMAT = "json"
const val RETROFIT_SEARCH = "search"


class MainPresenter(var mainView: MainView?, private val wikiService: WikiService) {
    private var disposable: Disposable? = null



    fun onDispose() {
        disposable?.dispose()
    }



    fun searchDefinition(query: String) {
        disposable = wikiService.loadDefinitions(RETROFIT_ACTION, RETROFIT_FORMAT, RETROFIT_SEARCH, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> mainView?.buildInfoDialog(result.query.search) },
                { error -> mainView?.showErrorToast(error.message ?: "Błąd") }
            )
    }
}