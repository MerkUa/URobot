package com.urobot.droid.ui.createbot

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.adapter.HomeBotAdapter
import com.urobot.droid.data.model.BotContentItem
import com.urobot.droid.data.model.BotData
import com.urobot.droid.data.model.ServiceButtons
import com.urobot.droid.ui.dialogs.CreateEventDialogFragment
import kotlinx.android.synthetic.main.activity_create_bot.*


class CreateBotActivity : AppCompatActivity(), CreateEventDialogFragment.ChangeDataListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var dataList: ArrayList<BotData> = ArrayList()
    private lateinit var adapter: HomeBotAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_bot)

        initAdapter()
        hideProgressBar()
//        eventDialogFragment?.setSelectedListener(this)
    }


    private fun initAdapter(){

//        dataList!!.add(BotData(emptyList(), 1, BotData.EMPTY_TYPE))
//
//        dataList!!.add(BotData(listOf(BotContentItem(123.toString(), 4.toString(), 5.toString(), listOf("1", "3"))), 3,
//            BotData.CONTENT_TYPE))

        val listContent: ArrayList<BotContentItem> = ArrayList()
        val list: ArrayList<ServiceButtons>? = ArrayList()
        listContent.add(BotContentItem(null, null, null, "", list))
        dataList.add(BotData(listContent, 1))

        adapter = HomeBotAdapter(this)
        adapter.setData(dataList)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = layoutManager
        rv_main.adapter = adapter


        /** ScrollListener */
        val lastVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv_main, newState)

                val totalItemCount = recyclerView.layoutManager?.itemCount

                if (totalItemCount == lastVisibleItemPosition + 1) {
                    Log.d("MyTAG", "Load new list")
                    rv_main.removeOnScrollListener(scrollListener)
                }
            }
        }
        rv_main.addOnScrollListener(scrollListener)

    }

    private  fun hideProgressBar(){
        pb_home.visibility = View.GONE
    }

    private fun showProgressBar(){
        pb_home.visibility = View.VISIBLE
    }

    override fun onDataChange(text: String, listButtons: ArrayList<ServiceButtons>?) {

        dataList.add(
            BotData(
                listOf(
                    BotContentItem(
                        null,
                        null,
                        null,
                        text,
                        listButtons
                    )
                ) as ArrayList<BotContentItem>, null
            )
        )
        adapter.setData(dataList)
    }

    override fun onBotDataChanged(botContentItem: BotContentItem) {
        Toast.makeText(this, botContentItem.description + ", bitch! ", Toast.LENGTH_SHORT).show()
    }
}