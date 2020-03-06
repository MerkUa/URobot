package com.urobot.droid.ui.fragments.ubot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.urobot.droid.R
import com.urobot.droid.adapter.BotListAdapter
import com.urobot.droid.data.model.Bot
import com.urobot.droid.ui.createbot.CreateBotActivity
import com.urobot.droid.ui.createbot.CreateBotActivity.Companion.EXTRA_BOT_ID
import com.urobot.droid.ui.dialogs.ChooseMessengerDialogFragment
import kotlinx.android.synthetic.main.fragment_ubot.*


class UbotFragment : Fragment(), BotListAdapter.ItemClickListener,
    ChooseMessengerDialogFragment.OnMessengerClickListener {

    private lateinit var ubotViewModel: UbotViewModel
    private val list = arrayListOf<Bot>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ubotViewModel =
            ViewModelProvider(this).get(UbotViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ubot, container, false)

        init()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createBotButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_create_bot)
        }
    }

    fun init() {
        ubotViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                ubotViewModel.getAllContentAndScripts(it.token!!, context!!)
            }
        })

        ubotViewModel.getAllScriptsLivaData.observe(viewLifecycleOwner, Observer { result ->

            list.clear()
            if (result.isEmpty()) {
                createBotButton.isEnabled = true

            } else {
                for (bot in result) {
                    list.add(
                        Bot(
                            bot.id!!,
                            bot.name!!,
                            bot.description!!,
                            bot.list!!,
                            ""
                        )
                    )
                }

                val adapterChats = BotListAdapter(context!!, list)
                adapterChats.addClickListener(this)
                listBot.layoutManager = LinearLayoutManager(context)
                listBot.adapter = adapterChats
//                createBotButton.isEnabled = false
            }
        })
    }

    override fun onItemClick(view: View?, position: Int) {
        var bot = list.get(position)
        val intent = Intent(activity, CreateBotActivity::class.java)
        intent.putExtra(EXTRA_BOT_ID, bot.botId)
        startActivityForResult(intent, 1)
    }

    override fun onAddBotClick(view: View?, position: Int) {
        var bot = list.get(position)
        val dialog = ChooseMessengerDialogFragment()
        dialog.setRobotId(bot.botId)
        dialog.setSelectedListener(this)
        dialog.show(activity!!.supportFragmentManager, "TAG")
    }

    override fun onClickListener(robotId: String, messengerId: Int, token: String, code: String) {
        ubotViewModel.addBot(robotId, messengerId, token, code, context!!)
    }
}