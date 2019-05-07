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

    private fun html2String(html: String): String = Jsoup.parse(html).text()


    private fun buildInfoDialog(elements: List<Model.Element>, ctx: Context) {
        val element = elements.random()
        val title = html2String(element.title)
        val snippet = html2String(element.snippet)
        var alert: AlertDialog? = null

        fun disimiss() {
            alert?.dismiss()
        }

        val dialogBuilder = AlertDialog.Builder(ctx)
        dialogBuilder.setMessage("$snippet.\n${ctx.getString(R.string.show_info_dialog_title)}")
            .setCancelable(false)
            .setPositiveButton(ctx.getString(R.string.positive_answer)) { _, _ ->  disimiss() }
            .setNegativeButton(ctx.getString(R.string.negative_answer)) { _, _ ->
                disimiss()
                val newElems = elements.toMutableList()
                newElems.remove(element)
                if (newElems.isEmpty())
                    Tools.makeToast(ctx.getString(R.string.no_more_definition_message), ctx)
                else {
                    Tools.makeToast(ctx.getString(R.string.load_new_definition_message), ctx)
                    buildInfoDialog(newElems.toList(), ctx)
                }

            }

        alert = dialogBuilder.create()
        alert.setTitle(title)


        alert.show()
    }

    private val keyWords = listOf("pies", "ekologia", "sudan", "kot", "tygrys", "katastrofa",
        "studnia", "dno", "pokot", "tulpa", "ił", "polska", "ziemniak", "korea północna")

    fun onClick(ctx: Context) {
        disposable = wikiApiServe.loadDefinitions("query", "json", "search", keyWords.random())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> buildInfoDialog(result.query.search, ctx) },
                { error -> Tools.makeToast(error.message ?: "Błąd", ctx) }
            )
    }
}