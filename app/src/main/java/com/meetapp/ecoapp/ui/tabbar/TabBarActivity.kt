package com.meetapp.ecoapp.ui.tabbar

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.ui.MainActivity


private const val TAG = "TBB"

class TabBarActivity : AppCompatActivity(), PhotoFragment.OnListFragmentInteractionListener {
    lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayShowTitleEnabled(false)

        setContentView(R.layout.activity_tab_bar)

        viewPager = findViewById(R.id.pager)
        tabs = findViewById(R.id.tabs)

        val fa = PagerAdapter(supportFragmentManager)
        viewPager.adapter = fa
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tabbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {
        R.id.action_home -> {
            val intent = Intent(this, MainActivity::class.java)
                .apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onListFragmentInteraction(item: PhotoLink) {
        when (tabs.selectedTabPosition) {
            1 -> {
                val curr = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.user_bad_choices), 1)
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(getString(R.string.user_bad_choices), curr + 1).apply()
            }
            2 -> {
                val curr = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.user_good_choices), 1)
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(getString(R.string.user_good_choices), curr + 1).apply()
            }
        }
        val intent = Intent(this, ImageActivity::class.java).apply { putExtra(IMG_URL_EXTRAS, item.url) }
        startActivity(intent)
    }
}
