package com.meetapp.ecoapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.meetapp.ecoapp.R
import com.squareup.picasso.Picasso
import uk.co.senab.photoview.PhotoViewAttacher

const val IMG_URL_EXTRAS = "imgUrl"
private const val TAG = "IA"

class ImageActivity : AppCompatActivity() {

    lateinit var view: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        view = findViewById(R.id.photo_view)
        view.isClickable = true
        view.setOnClickListener { finish() }

        val extras =  intent.extras
        if (extras == null) {
            Log.d(TAG, "No extras in intent")
            Toast.makeText(this, "Wystąpił błąd url!", Toast.LENGTH_SHORT).show()
            finish()
        }

        val url = extras!!.getString(IMG_URL_EXTRAS)
        if (url == null) {
            Log.d(TAG, "No url passed")
            Toast.makeText(this, "Wystąpił błąd url!", Toast.LENGTH_SHORT).show()
            finish()
        }

        Log.d(TAG, "Showing image from: $url")
        Picasso.get().load(url).into(view)
        PhotoViewAttacher(view)
    }


}
