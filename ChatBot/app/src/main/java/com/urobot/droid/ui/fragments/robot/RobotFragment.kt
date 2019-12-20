package com.urobot.droid.ui.fragments.robot

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import android.widget.Toast
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.urobot.droid.R
import com.urobot.droid.adapter.RobotPageAdapter
import kotlinx.android.synthetic.main.robot_fragment.*

class RobotFragment : Fragment() {

    private lateinit var viewModel: RobotViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d("fragment", "onCreateView")
        val root = inflater.inflate(R.layout.robot_fragment, container, false)


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RobotViewModel::class.java)
        // TODO: Use the ViewModel
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

    }

}
