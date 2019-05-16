package com.meetapp.ecoapp.ui.main

import android.view.View

interface MainView {
    fun giveDefinition(view: View)
    fun buildInfoDialog(elements: List<Model.Element>)
}