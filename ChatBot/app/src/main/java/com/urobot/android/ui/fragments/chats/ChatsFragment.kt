package com.urobot.android.ui.fragments.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.urobot.android.R
import com.urobot.android.adapter.ChatListAdapter
import com.urobot.android.data.model.Chat
import kotlinx.android.synthetic.main.fragment_chats.*

class ChatsFragment : Fragment(), ChatsViewModel.IChatsContract {

    private lateinit var chatsViewModel: ChatsViewModel
    var listOfChat = arrayListOf<Chat>()
//    val myViewModel: ChatsViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chatsViewModel =
                ViewModelProvider(this).get(ChatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chats, container, false)


        chatsViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                chatsViewModel.getContactId(it.token!!)
            }
        })
        chatsViewModel.setListener(this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listChat.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val chat = listOfChat.get(position)
                if (chat.botId != 0) {
                    val action = ChatsFragmentDirections.actionNavigationChatsToNavigationMessages()
                        .setIdRecipient(chat.chatId).setBotId(chat.botId)
                    view.findNavController().navigate(action)
                } else {
                    val action = ChatsFragmentDirections.actionNavigationChatsToNavigationMessages()
                        .setContact(chat.chatId).setBotId(chat.botId)
                    view.findNavController().navigate(action)
                }
            }



    }

    override fun onGetContactsResult(list: ArrayList<Chat>) {
        if (list.isEmpty()) {
            chatsTips.visibility = View.VISIBLE

        } else {
            listOfChat = list
            val adapterChats = ChatListAdapter(context!!, list)
            listChat.adapter = adapterChats
        }

    }
}