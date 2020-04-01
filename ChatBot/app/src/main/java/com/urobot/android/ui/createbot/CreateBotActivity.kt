package com.urobot.android.ui.createbot

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urobot.android.R
import com.urobot.android.adapter.HomeBotAdapter
import com.urobot.android.data.model.BotContentItem
import com.urobot.android.data.model.BotData
import com.urobot.android.data.model.ServiceButtons
import com.urobot.android.ui.dialogs.CreateEventDialogFragment
import kotlinx.android.synthetic.main.activity_create_bot.*


class CreateBotActivity : AppCompatActivity(), CreateEventDialogFragment.ChangeDataListener {

    companion object {
        const val EXTRA_BOT_ID = "BOTID"
        private const val TAG = "MainActivity"
    }

    private var dataList: ArrayList<BotData> = ArrayList()
    private lateinit var adapter: HomeBotAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var createBotViewModel: CreateBotViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_bot)

        createBotViewModel =
            ViewModelProvider(this).get(CreateBotViewModel::class.java)

        initAdapter()
        hideProgressBar()

        val botId = intent.getStringExtra(EXTRA_BOT_ID)
        adapter.setBotId(botId)
        createBotViewModel.setBotId(botId.toInt())
    }


    private fun initAdapter() {

        val listContent: ArrayList<BotContentItem> = ArrayList()



        adapter = HomeBotAdapter(this)
        rv_main.adapter = adapter
        rv_main.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_main.layoutManager = layoutManager


        /**Get All Data */
        createBotViewModel.currentUser.observe(this, Observer { users ->
            users?.let {
                createBotViewModel.getAllContentAndScripts(it.token!!)
            }
        })

        createBotViewModel.getAllScriptsLivaData.observe(this, Observer { result ->
            dataList.clear()
            if (result.isEmpty()) {
                val list: ArrayList<ServiceButtons>? = ArrayList()
                listContent.add(BotContentItem(1, 0, 1, null, -1, true, "", "", list))
                dataList.add(BotData(listContent))
                adapter.setData(dataList)
            }

            for (level in result) {
                val listLevelContent: ArrayList<BotContentItem> = ArrayList()
                for (item in level) {
                    val list: ArrayList<ServiceButtons>? = ArrayList()
                    for (button in item.buttons!!) {
                        list?.add(
                            ServiceButtons(
                                button.serviceId,
                                button.name
                            )
                        )
                    }


                    listLevelContent.add(
                        BotContentItem(
                            item.uid?.toLong(),
                            item.parentUid?.toLong(),
                            item.level,
                            null,
                            -1,
                            item.empty!!,
                            item.text,
                            item.data,
                            list
                        )
                    )

                }
                dataList.add(BotData(listLevelContent))
            }
            adapter.setData(dataList)

//            for (item in result) {
//                Log.d("merk","getAllScriptsLivaData "+result.toString())

//                if(item.level == 1) {
//                    listContent.add(
//                        BotContentItem(
//                            item.uid,
//                            item.parentUid,
//                            null,
//                            -1,
//                            false,
//                            item.messages!![0].data!!,
//                            list
//                        )
//                    )
//                }

//                   list!!.add(ServiceButtons(result.messages!![item].buttons!![item].serviceId))

//                   listContent.add(BotContentItem(result.uid, result.parentUid, null, -1, true,
//                       result.messages!![item].data!!,list))

//                   dataList.add(BotData(listContent))
//                   adapter.setData(dataList)

//                Log.d("dataList", dataList.size.toString())


//                for(message in result[item].messages!!){

//                    adapter.insertData(BotContentItem(result[item].uid, result[item].parentUid, null, -1, false, message.data!!, list))

//                    Log.d("merk", "dataList "+ item)
//                    Log.d("merk", "parentUid "+ result[item].parentUid)
//                    listContent.add(
//                        BotContentItem(result[item].uid, result[item].parentUid, null, -1, false, message.data!!, list)
//                    )
//                }
//                dataList.add(BotData(listContent))


//                for (i in result[item].messages!!.indices) {
//                    adapter.addData(
//                        BotContentItem(
//                            result[item].uid, result[item].parentUid, null, -1, false,
//                            result[item].messages!![i].data!!, list
//                        )
//                    )
//                }
//                adapter.setData(dataList)

//            }

//            listContent.add(BotContentItem(1, 0, null, -1, true, "", list))
//            dataList.add(BotData(listContent))
//            adapter.setData(dataList)



//        else{
//            listContent.add(BotContentItem(1, null, null, -1, true, "", list))
//            dataList.add(BotData(listContent))
//            adapter.setData(dataList)
//        }
        })


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

    private fun hideProgressBar() {
        pb_home.visibility = View.GONE
    }

    private fun showProgressBar() {
        pb_home.visibility = View.VISIBLE
    }

    override fun onBotDataCreated(botContentItem: BotContentItem) {
        adapter.addData(botContentItem)

        /** Create New Bot Content */
        createBotViewModel.currentUser.observe(this, androidx.lifecycle.Observer { users ->
            users?.let {
                createBotViewModel.createBotContentAndScripts(it.token!!, botContentItem)
            }
        })
    }

    override fun onBotDataChanged(botContentItem: BotContentItem) {
        adapter.addData(botContentItem)

        /** Create New Bot Content */
        createBotViewModel.currentUser.observe(this, androidx.lifecycle.Observer { users ->
            users?.let {
                createBotViewModel.createBotContentAndScripts(it.token!!, botContentItem)
            }
        })
    }

    override fun onBotDeleted(botContentItem: BotContentItem) {
        createBotViewModel.currentUser.observe(this, androidx.lifecycle.Observer { users ->
            users?.let {
                Log.d("Merk", "botContentItem " + botContentItem.level)
                createBotViewModel.deleteBotContentAndScripts(it.token!!, botContentItem)
            }
        })
    }
}