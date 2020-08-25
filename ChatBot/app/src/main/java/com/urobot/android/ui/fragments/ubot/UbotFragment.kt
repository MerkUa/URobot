package com.urobot.android.ui.fragments.ubot

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
import com.urobot.android.R
import com.urobot.android.adapter.BotListAdapter
import com.urobot.android.data.model.Bot
import com.urobot.android.ui.createbot.CreateBotActivity
import com.urobot.android.ui.createbot.CreateBotActivity.Companion.EXTRA_BOT_ID
import com.urobot.android.ui.dialogs.ChooseMessengerDialogFragment
import com.urobot.android.ui.dialogs.EditBotDialogFragment
import kotlinx.android.synthetic.main.fragment_ubot.*


class UbotFragment : Fragment(), BotListAdapter.ItemClickListener,
    ChooseMessengerDialogFragment.OnMessengerClickListener,
    EditBotDialogFragment.OnEditClickListener {

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
                listBot.adapter?.notifyDataSetChanged()
                robotTips.visibility = View.VISIBLE

            } else {
                robotTips.visibility = View.GONE
                for (bot in result) {
                    list.add(
                        Bot(
                            bot.id!!,
                            bot.name!!,
                            bot.description!!,
                            bot.list!!,
                            "",
                            bot.bots
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

    override fun onChangeClick(view: View?, position: Int) {

        var bot = list.get(position)
        val dialog = EditBotDialogFragment()
        if (list.isNotEmpty()) {
            dialog.setRobot(bot)
        }
        dialog.setSelectedListener(this)
        dialog.show(activity!!.supportFragmentManager, "TAG")

    }

    override fun shareBot(link: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onClickListener(robotId: String, messengerId: Int, token: String, code: String) {
        ubotViewModel.addBot(robotId, messengerId, token, code, context!!)
    }

    override fun messengerAdded() {
        ubotViewModel.update(context!!)
    }

    override fun onEditListener(
        robotId: String,
        title: String,
        description: String,
        repeat: String
    ) {
        ubotViewModel.editBot(robotId, title, description, repeat, context!!)
    }

    override fun onDeleteListener(robotId: String) {
        ubotViewModel.deleteBot(robotId, context!!)
    }
}