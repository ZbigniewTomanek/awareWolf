package com.meetapp.ecoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
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

        // TODO dodać resztę opcji z menu

        else -> super.onOptionsItemSelected(item)

    }

    fun start(view: View) {
        val intent = Intent(this, TabBarActivity::class.java)
        startActivity(intent)
    }

    fun showPhoto(url: String) {
        val intent = Intent(this, ImageActivity::class.java).apply { putExtra(IMG_URL_EXTRAS, url) }
        startActivity(intent)
    }
}
