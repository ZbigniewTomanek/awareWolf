package com.meetapp.ecoapp.utils

import android.content.Context
import android.widget.Toast

object Tools {
    fun makeToast(text: String, ctx: Context) {
        val toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}