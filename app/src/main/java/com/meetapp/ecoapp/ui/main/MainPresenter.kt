package com.meetapp.ecoapp.ui.main

import android.content.Context
import com.meetapp.ecoapp.dagger.BasePresenterInjector

import com.meetapp.ecoapp.network.WikiService
import com.meetapp.ecoapp.utils.Tools
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter(var mainView: MainView?): BasePresenterInjector() {

    private var disposable: Disposable? = null

    @Inject
    lateinit var wikiService: WikiService

    fun onDispose() {
        disposable?.dispose()
    }


    private val keyWords = listOf("pies", "ekologia", "sudan", "kot", "tygrys", "katastrofa",
        "studnia", "dno", "pokot", "tulpa", "ił", "polska", "ziemniak", "korea północna")

    fun onClick(ctx: Context) {
        disposable = wikiService.loadDefinitions("query", "json", "search", keyWords.random())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> mainView?.buildInfoDialog(result.query.search) },
                { error -> Tools.makeToast(error.message ?: "Błąd", ctx) }
            )
    }
}