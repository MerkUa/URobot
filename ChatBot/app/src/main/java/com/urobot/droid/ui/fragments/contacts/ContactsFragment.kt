package com.urobot.droid.ui.fragments.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.urobot.droid.R
import com.urobot.droid.adapter.ContactListAdapter
import com.urobot.droid.adapter.ContactPageAdapter
import com.urobot.droid.data.model.Contact
import kotlinx.android.synthetic.main.robot_fragment.*


class ContactsFragment : Fragment(), ContactListAdapter.ItemClickListener {


    private val list = arrayListOf<Contact>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)
        return root
    }

    override fun onItemClick(view: View?, position: Int) {
        var contact = list.get(position)
        val action =
            ContactsFragmentDirections.action_navigation_contacts_to_navigation_profile2(
                contact.name,
                contact.avatar,
                contact.phone
            )
//        val action = AddMessengerFragmentDirections.Navigation_to_add_messenger(TELEGTAM)
        findNavController().navigate(action)
        Log.d("onItemClick", "onItemClick " + position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("fragment", "onViewCreated")
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.tabs_my_contacts)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.tabs_server_contacts)))

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ContactPageAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

}