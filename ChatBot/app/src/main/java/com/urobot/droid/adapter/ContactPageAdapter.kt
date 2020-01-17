package com.urobot.droid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.urobot.droid.ui.fragments.contacts.ContactsFromServerFragment
import com.urobot.droid.ui.fragments.contacts.ContactsMyFragment
import com.urobot.droid.ui.fragments.ubot.UbotFragment

class ContactPageAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {
    private val PAGES_COUNT = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ContactsFromServerFragment()
            1 -> ContactsMyFragment()
            else -> UbotFragment()
        }
    }

    override fun getCount(): Int {
        return PAGES_COUNT
    }
}