package com.urobot.android

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.urobot.android.adapter.TutorialPageAdapter
import com.urobot.android.ui.login.LoginActivity


class TutorialActivity : AppCompatActivity() {

    private var mAdapter: TutorialPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
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
