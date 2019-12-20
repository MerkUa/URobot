package com.urobot.droid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.urobot.droid.R
import com.urobot.droid.fragment.TutorialPageFragment
import com.urobot.droid.ui.fragments.ubot.UbotFragment
import com.urobot.droid.ui.fragments.ubotservice.UbotServiceFragment
import kotlinx.android.synthetic.main.fragment_tutorial_page.view.*

class RobotPageAdapter(fm: FragmentManager, behavior: Int) :
        FragmentStatePagerAdapter(fm, behavior) {
    private val PAGES_COUNT = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UbotFragment()
            1 -> UbotServiceFragment()
            else -> UbotFragment()
        }
    }

    override fun getCount(): Int {
        return PAGES_COUNT
    }
}