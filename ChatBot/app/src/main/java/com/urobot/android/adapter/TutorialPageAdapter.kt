package com.urobot.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.urobot.android.R
import com.urobot.android.fragment.TutorialPageFragment

class TutorialPageAdapter(fm: FragmentManager, behavior: Int) :
        FragmentStatePagerAdapter(fm, behavior) {
    private val PAGES_COUNT = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TutorialPageFragment.newInstance(R.string.title_fragment_1, R.drawable.tutorial1)
            1 -> TutorialPageFragment.newInstance(R.string.title_fragment_2, R.drawable.tutorial2)
            2 -> TutorialPageFragment.newInstance(R.string.title_fragment_3, R.drawable.tutorial3)
            else -> TutorialPageFragment.newInstance(
                R.string.title_fragment_1,
                R.drawable.tutorial_page
            )
        }
    }

    override fun getCount(): Int {
        return PAGES_COUNT
    }
}