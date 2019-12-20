package com.urobot.droid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.urobot.droid.adapter.TutorialPageAdapter
import com.urobot.droid.ui.login.LoginActivity


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class TutorialActivity : AppCompatActivity() {

    private var mAdapter: TutorialPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tutorial)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val pager = findViewById<ViewPager>(R.id.pager)
        val tabLayout = findViewById<TabLayout>(R.id.dots_tab_layout)
        val skip = findViewById<TextView>(R.id.buttonSkip)

        mAdapter = TutorialPageAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        pager.adapter = mAdapter
        tabLayout.setupWithViewPager(pager)
        skip.setOnClickListener {
            startLogin()
        }

    }


    private fun startLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}
