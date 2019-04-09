package com.meetapp.ecoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Camera)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, CameraFragment.newInstance())
            .commit()
    }
}
