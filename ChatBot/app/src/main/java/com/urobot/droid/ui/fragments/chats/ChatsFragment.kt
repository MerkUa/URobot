package com.urobot.droid.ui.fragments.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.urobot.droid.R
import com.urobot.droid.adapter.ChatListAdapter
import com.urobot.droid.data.model.Chat
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

//        list.add(Chat("1", "Android", "http://android.com.ua/images/News/android_logo.png", true, true, "12:07", "android forever"))
//        list.add(Chat("2", "iOs", "https://cdn.mos.cms.futurecdn.net/MFHVMYQCeJHMGop4u8VkzJ.jpg", false, false, "12:01", "apple apple apple"))
//        list.add(Chat("3", "Xiaomi", "https://i.pinimg.com/originals/68/ab/1e/68ab1e35d6558b90c67f7a3c2c9e90aa.jpg", true, true, "11:00", "top za svoi dengi"))
//        list.add(Chat("4", "Huawei", "https://servicefon.lviv.ua/wp-content/uploads/2014/12/huawei-logo-300x300.jpeg", true, false, "14:58", "prosto huawei"))



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
                val action = ChatsFragmentDirections.actionNavigationChatsToNavigationMessages().setIdRecipient(chat.chatId)
                view.findNavController().navigate(action)
            }


    }

    override fun onGetContactsResult(list: ArrayList<Chat>) {
        listOfChat = list
        val adapterChats = ChatListAdapter(context!!, list)
        listChat.adapter = adapterChats

    }
}