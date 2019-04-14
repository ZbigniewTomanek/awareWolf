package com.meetapp.ecoapp.ui.tabbar


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.meetapp.ecoapp.ui.tabbar.PhotoLink

class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val presentPhotos = listOf(
        PhotoLink(
            "Terroryzm",
            "http://johnwelbourn.powerathletehq.com/files/2013/09/9-11-Day-After-Talk-To-Me-Johnnie.jpg"
        ),
        PhotoLink(
            "Wielkie zagrożenia",
            "https://cdn.vox-cdn.com/thumbor/CTUa_1-F_-F658J0POYXxHIJduY=/0x0:3500x2336/1200x800/filters:focal(1234x201:1794x761)/cdn.vox-cdn.com/uploads/chorus_image/image/59663021/India_Smog_pollution_Delhi.0.jpg"
        ),
        PhotoLink(
            "Technologia jutra dziś",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.extremetech.com%2Fwp-content%2Fuploads%2F2015%2F04%2FLHC-640x353.jpg&f=1"
        ),
        PhotoLink(
            "Wojny",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.aq8n9G99gENLzZxAzZBXBwHaE7%26pid%3D15.1&f=1"
        ),
        PhotoLink(
            "Wielkie szanse",
            "https://www.wykop.pl/cdn/c3201142/comment_Jkchu6LsqiYq1WAFY4cOYqPob9Vk6IU8,w400.jpg"
        )
    )
    private val pastPhotos = listOf(
        PhotoLink(
            "Utopie",
            "http://johnwelbourn.powerathletehq.com/files/2013/09/9-11-Day-After-Talk-To-Me-Johnnie.jpg"
        ),
        PhotoLink(
            "Przeludnienie",
            "https://i2.wp.com/www.bms.co.in/wp-content/uploads/2015/02/overpopulation.jpg"
        ),
        PhotoLink(
            "Masowa zagłada",
            "https://proxy.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.citymetric.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fnodeimage%2Fpublic%2Farticle_2015%2F11%2Fgettyimages-73553620.jpg%3Fitok%3DHavY_TbV&f=1"
        ),
        PhotoLink(
            "Ignorancja",
            "https://www.monsterchildren.com/wp-content/uploads/2017/02/MC-isis-homer.jpg"
        ),
        PhotoLink(
            "Złe moce",
            "https://4.bp.blogspot.com/-rCQ9Nq_8zU8/XKPzSBj6_DI/AAAAAAAACcw/qSuqUuMOpBw_lFe1YaRbvEdgQA5oDN-KACLcBGAs/w1200-h630-p-k-no-nu/stosy.jpg"
        )
    )

    private val futurePhotos = listOf(
        PhotoLink(
            "Zrównoważnoy rozwój",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.iLxmVsDh1kmNHOJ17QmInQHaET%26pid%3D15.1&f=1"
        ),
        PhotoLink(
            "Podbój kosmosu",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.37Jnqx3e_-aRf8tA4jUvLwHaEK%26pid%3D15.1&f=1"
        ),
        PhotoLink(
            "Energia słońca na ziemii",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.iter.org%2Fimg%2Fcrop-2000-90%2Fall%2Fcontent%2Fcom%2Fimg_galleries%2Fin-cryostat-section_rev-3_small.jpg&f=1"
        ),
        PhotoLink(
            "Wspólnota",
            "https://proxy.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.brockpress.com%2Fwp-content%2Fuploads%2F2015%2F03%2Fcoexist-2.jpg&f=1"
        ),
        PhotoLink(
            "Transhumanizm",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.uzegrlgeBMg9EqKPf8zKbQHaEo%26pid%3D15.1&f=1"
        )
    )


    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        val pf = PhotoFragment()

        when (position) {
            0 -> pf.passItemList(presentPhotos)
            1 -> pf.passItemList(pastPhotos)
            else -> pf.passItemList(futurePhotos)
        }

        return pf
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> "Jesteś tu"
        1 -> "Wolisz być tu?"
        else -> "Czy tu?"
    }
}