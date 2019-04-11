package com.meetapp.ecoapp.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.ui.camera.CameraActivity
import org.achartengine.GraphicalView
import org.achartengine.chart.PieChart
import org.achartengine.model.CategorySeries
import org.achartengine.renderer.DefaultRenderer
import org.achartengine.renderer.SimpleSeriesRenderer

class MainActivity : AppCompatActivity() {
    private val COLORS = intArrayOf(Color.rgb(56, 128, 58), Color.rgb(174,0,0), Color.rgb(69, 90, 100))
    private val NAME_LIST = arrayOf("Dobro:", "Zło:")

    lateinit var pieChartView : LinearLayout
    private var mSeries = CategorySeries("")
    private val mRenderer = DefaultRenderer()
    private var mChartView : GraphicalView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(R.layout.activity_main)


        pieChartView = findViewById(R.id.pieChartView)
        setPieChart()
    }

    private fun setPieChart()
    {
        val numOfCorrect = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.user_good_choices), 1)
        val namOfIncorrect = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.user_bad_choices), 1)

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

        if(numOfCorrect == 0 && namOfIncorrect == 0)
        {
            mRenderer.isShowLabels = false
            mSeries.add("", 1.toDouble())
            val rendererLack = SimpleSeriesRenderer()
            rendererLack.color = COLORS[2]
            mRenderer.addSeriesRenderer(rendererLack)
        }
        else
        {
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
            pieChartView.removeView(mChartView)
        }

        val pieChart = PieChart(mSeries, mRenderer)
        mChartView = GraphicalView(applicationContext, pieChart)
        pieChartView.addView(mChartView)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        setPieChart()
        super.onResume()
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

    fun start(view: View) {
        val intent = Intent(this, CameraActivity::class.java) //TODO
        startActivity(intent)
    }

}
