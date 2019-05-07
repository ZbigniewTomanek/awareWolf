package com.meetapp.ecoapp.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast

object Tools {
    fun makeToast(text: String, ctx: Context) {
        val toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT)
        val tv = toast.view.findViewById<TextView>(android.R.id.message)
        tv.setBackgroundResource(android.R.color.transparent)
        toast.show()
    }
}