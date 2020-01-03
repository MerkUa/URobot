package com.urobot.droid.ui.createbot

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.adapter.HomeBotAdapter
import com.urobot.droid.data.model.BotData
import java.util.*

class CreateBotActivity : AppCompatActivity() {
    private var dataList: ArrayList<BotData>? = null
    private var progressBar: ProgressBar? = null
    private lateinit var adapter: HomeBotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_bot)
        dataList = ArrayList<BotData>()
        adapter = HomeBotAdapter(dataList!!, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_main)
        progressBar = findViewById(R.id.pb_home)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setAdapter(adapter)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}