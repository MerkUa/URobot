package com.urobot.droid.ui.fragments.ubot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.urobot.droid.R
import com.urobot.droid.adapter.BotListAdapter

import com.urobot.droid.data.model.Bot
import kotlinx.android.synthetic.main.fragment_ubot.*

class UbotFragment : Fragment() {

    private lateinit var ubotViewModel: UbotViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ubotViewModel =
                ViewModelProvider(this).get(UbotViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ubot, container, false)

        val listChat: ListView = root.findViewById(R.id.listBot)
        val list = arrayListOf<Bot>()
        list.add(Bot("1", "test", "testDescription", emptyList(), ""))

        val adapterChats = BotListAdapter(context!!, list)
        listChat.adapter = adapterChats

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createBotButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_create_bot)
        }
    }
}