package com.meetapp.ecoapp.ui.main

import android.content.Context

import androidx.appcompat.app.AlertDialog
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.utils.Tools
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup

class MainPresenter(var mainView: MainView?) {

    private var disposable: Disposable? = null

    private val wikiApiServe by lazy {
        WikiService.create()
    }

    fun onDispose() {
        disposable?.dispose()
    }


    private val keyWords = listOf("pies", "ekologia", "sudan", "kot", "tygrys", "katastrofa",
        "studnia", "dno", "pokot", "tulpa", "ił", "polska", "ziemniak", "korea północna")

    fun onClick(ctx: Context) {
        disposable = wikiApiServe.loadDefinitions("query", "json", "search", keyWords.random())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> mainView?.buildInfoDialog(result.query.search) },
                { error -> Tools.makeToast(error.message ?: "Błąd", ctx) }
            )
    }
}