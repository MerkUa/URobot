package com.urobot.droid.ui.fragments.ubot

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_ubot.*


class UbotFragment : Fragment(), BotListAdapter.ItemClickListener {

    private lateinit var ubotViewModel: UbotViewModel

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
                Log.d("Merk", "users")

                ubotViewModel.getAllContentAndScripts(it.token!!)
            }
        })

        ubotViewModel.getAllScriptsLivaData.observe(viewLifecycleOwner, Observer { result ->
            if (result.isEmpty()) {
                createBotButton.isEnabled = true

            } else {
                val list = arrayListOf<Bot>()
                list.add(Bot("1", "test", "testDescription", emptyList(), ""))

                val adapterChats = BotListAdapter(context!!, list)
                adapterChats.addClickListener(this)
                listBot.layoutManager = LinearLayoutManager(context)
                listBot.adapter = adapterChats
                createBotButton.isEnabled = false
            }
        })
    }

    override fun onItemClick(view: View?, position: Int) {
        val intent = Intent(activity, CreateBotActivity::class.java)
        startActivityForResult(intent, 1)
    }
}