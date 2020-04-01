package com.urobot.android.ui.fragments.robot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.urobot.android.R
import com.urobot.android.adapter.RobotPageAdapter
import kotlinx.android.synthetic.main.robot_fragment.*

class RobotFragment : Fragment() {

    private lateinit var robotViewModel: RobotViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d("fragment", "onCreateView")
        robotViewModel = ViewModelProvider(this).get(RobotViewModel::class.java)
        val root = inflater.inflate(R.layout.robot_fragment, container, false)


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("fragment", "onActivityCreated")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment", "onCreate")

//        val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
//        val viewPager = root.findViewById<ViewPager>(R.id.viewPager)

    }

    override fun onResume() {
        super.onResume()
        Log.d("fragment", "onResume")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("fragment", "onViewCreated")
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.tabs_robots)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.tabs_scripts)))

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = RobotPageAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
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

        robotViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                robotViewModel.registerDeviceId(context!!, it.token!!)
            }
        })

    }

}
