package com.meetapp.ecoapp.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.facebook.stetho.Stetho
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.ui.camera.CameraActivity
import com.meetapp.ecoapp.ui.routines.RoutinesListActivity
import com.meetapp.ecoapp.ui.tabbar.TabBarActivity
import com.meetapp.ecoapp.utils.Tools
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.achartengine.GraphicalView
import org.achartengine.chart.PieChart
import org.achartengine.model.CategorySeries
import org.achartengine.renderer.DefaultRenderer
import org.achartengine.renderer.SimpleSeriesRenderer
import org.jsoup.Jsoup
import javax.inject.Inject


private const val TAG = "MainActivity"
class MainActivity : DaggerAppCompatActivity(), MainView {

    private val COLORS = intArrayOf(Color.rgb(56, 128, 58), Color.rgb(174,0,0), Color.rgb(69, 90, 100))
    private val NAME_LIST = arrayOf("Dobro:", "Zło:")

    private var mSeries = CategorySeries("")
    private val mRenderer = DefaultRenderer()
    private var mChartView : GraphicalView? = null

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var presenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        Stetho.initializeWithDefaults(this)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(R.layout.activity_main)

        presenter.attach(this)

        setPieChart()
    }

    private fun setPieChart() {
        val numOfCorrect = preferences.getInt(getString(R.string.user_good_choices), 1)
        val namOfIncorrect = preferences.getInt(getString(R.string.user_bad_choices), 1)

        mSeries.clear()
        mRenderer.removeAllRenderers()


        mSeries = CategorySeries("")
        mRenderer.isApplyBackgroundColor = false
        mRenderer.isShowLegend = false
        mRenderer.isShowLabels = false
        mRenderer.startAngle = 90.0f
        mRenderer.isClickEnabled = false
        mRenderer.isZoomEnabled = false
        mRenderer.isPanEnabled = false

        if(numOfCorrect == 0 && namOfIncorrect == 0) {
            mRenderer.isShowLabels = false
            mSeries.add("", 1.toDouble())
            val rendererLack = SimpleSeriesRenderer()
            rendererLack.color = COLORS[2]
            mRenderer.addSeriesRenderer(rendererLack)
        }
        else {
            mSeries.add("${NAME_LIST[0]} $numOfCorrect", numOfCorrect.toDouble())
            val rendererCorrect = SimpleSeriesRenderer()
            rendererCorrect.color = COLORS[0]
            mRenderer.addSeriesRenderer(rendererCorrect)

            mSeries.add("${NAME_LIST[1]} $namOfIncorrect", namOfIncorrect.toDouble())
            val rendererWrong = SimpleSeriesRenderer()
            rendererWrong.color = COLORS[1]
            mRenderer.addSeriesRenderer(rendererWrong)
        }



        if (mChartView != null) {
            pie_chart_view.removeView(mChartView)
        }

        val pieChart = PieChart(mSeries, mRenderer)
        mChartView = GraphicalView(applicationContext, pieChart)
        pie_chart_view.addView(mChartView)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        setPieChart()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDispose()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {
        R.id.action_contact -> {
            val mail = Intent(Intent.ACTION_SEND_MULTIPLE)
            mail.putExtra(Intent.EXTRA_EMAIL, arrayOf("shadow.tesseract.studio@gmail.com"))
            mail.putExtra(Intent.EXTRA_SUBJECT, "AwareWolf")
            mail.type = "message/rfc822"
            startActivity(mail)
            finish()
            true
        }

        R.id.action_info -> {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Jestem wilkiem, który osiągnął samoświadomość czwartej gęstości i poznał tajemnicę przyszłości")
                .setCancelable(false)
                .setPositiveButton("Wystarczy") { _, _ -> closeContextMenu() }
                .setNegativeButton("Chcę więcej") { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://iluminaci.pl/"))
                    startActivity(intent)
                }

            val alert = dialogBuilder.create()
            alert.setTitle("Więc tak")
            alert.show()
            true
        }

        else -> super.onOptionsItemSelected(item)

    }

    fun startCamera(view: View) {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    fun startTabbar(view: View) {
        val intent = Intent(this, TabBarActivity::class.java)
        startActivity(intent)
    }

    fun startRoutines(view: View) {
        val intent = Intent(this, RoutinesListActivity::class.java)
        startActivity(intent)
    }

    private val keyWords = listOf("pies", "ekologia", "sudan", "kot", "tygrys", "katastrofa",
        "studnia", "dno", "pokot", "tulpa", "ił", "polska", "ziemniak", "korea północna")

    override fun giveDefinition(view: View) {
        presenter.searchDefinition(keyWords.random())
    }

    override fun showErrorToast(message: String) {
        Tools.makeToast(message, this)
    }

    private fun html2String(html: String): String = Jsoup.parse(html).text()
    override fun buildInfoDialog(elements: List<WikiModel.Element>) {
        val element = elements.random()
        val title = html2String(element.title)
        val snippet = html2String(element.snippet)
        var alert: AlertDialog? = null

        fun disimiss() {
            alert?.dismiss()
        }

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("$snippet.\n${this.getString(R.string.show_info_dialog_title)}")
            .setCancelable(false)
            .setPositiveButton(this.getString(R.string.positive_answer)) { _, _ ->  disimiss() }
            .setNegativeButton(this.getString(R.string.negative_answer)) { _, _ ->
                disimiss()
                val newElems = elements.toMutableList()
                newElems.remove(element)
                if (newElems.isEmpty())
                    Tools.makeToast(this.getString(R.string.no_more_definition_message), this)
                else {
                    Tools.makeToast(this.getString(R.string.load_new_definition_message), this)
                    buildInfoDialog(newElems.toList())
                }

            }

        alert = dialogBuilder.create()
        alert.setTitle(title)


        alert.show()
    }


}
