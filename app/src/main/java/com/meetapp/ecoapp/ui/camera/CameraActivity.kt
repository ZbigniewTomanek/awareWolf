package com.meetapp.ecoapp.ui.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meetapp.ecoapp.R

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
