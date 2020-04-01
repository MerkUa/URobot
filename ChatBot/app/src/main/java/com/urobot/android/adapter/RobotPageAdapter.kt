package com.urobot.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.urobot.android.ui.fragments.ubot.UbotFragment
import com.urobot.android.ui.fragments.ubotservice.ServicesFragment

class RobotPageAdapter(fm: FragmentManager, behavior: Int) :
        FragmentStatePagerAdapter(fm, behavior) {
    private val PAGES_COUNT = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UbotFragment()
            1 -> ServicesFragment()
            else -> UbotFragment()
        }
    }

    override fun getCount(): Int {
        return PAGES_COUNT
    }
}