package com.meetapp.ecoapp.ui.main

import com.meetapp.ecoapp.network.WikiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val RETROFIT_ACTION = "query"
const val RETROFIT_FORMAT = "json"
const val RETROFIT_SEARCH = "search"


class MainPresenter(val wikiService: WikiService) {
    private var disposable: Disposable? = null

    lateinit var mainView: MainView

    fun attach(view: MainView) {
        this.mainView = view
    }


    fun onDispose() {
        disposable?.dispose()
    }

    fun searchDefinition(query: String) {

        if (query == "") {
            mainView.showErrorToast("Zapytanie nie może być puste")
            return
        }

        disposable = wikiService.loadDefinitions(RETROFIT_ACTION, RETROFIT_FORMAT, RETROFIT_SEARCH, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> mainView.buildInfoDialog(result.query.search) },
                { error -> mainView.showErrorToast(error.message ?: "Błąd") }
            )
    }
}