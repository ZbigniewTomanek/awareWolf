package com.meetapp.ecoapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val presentPhotos = listOf<PhotoLink>(
        PhotoLink("Dzik", "https://www.wykop.pl/cdn/c3201142/comment_2s5K5tK104TFKGnfRGL3sKhTmuy6xIMc,w400.jpg")
    )
    private val pastPhotos = listOf<PhotoLink>(
        PhotoLink("xD", "https://www.wykop.pl/cdn/c3201142/comment_0TS64kBxgYgvpaSPyVzig5a5tpfCto1A,w400.jpg")
    )
    private val futurePhotos = listOf<PhotoLink>(
        PhotoLink("Sredniowiecze", "https://www.wykop.pl/cdn/c3201142/comment_tj2VldjmefvW3aM7l8zfEhOHVWBnmhFW,w400.jpg")
    )

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        val pf = PhotoFragment()

        when (position) {
            0 -> pf.passItemList(pastPhotos)
            1 -> pf.passItemList(presentPhotos)
            else -> pf.passItemList(futurePhotos)
        }

        return pf
    }

    override fun getPageTitle(position: Int) = when (position) {
        1 -> "Wolisz być tu?"
        2 -> "Jesteś tu"
        else -> "Czy tu?"
    }
}