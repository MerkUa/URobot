package com.urobot.droid.ui.fragments.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.urobot.droid.R
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.adapter.ChatListAdapter
import com.urobot.droid.data.model.Chat
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.message.MessageFragment
import com.urobot.droid.ui.fragments.support.SupportFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel
//    val myViewModel: ChatsViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chatsViewModel =
                ViewModelProvider(this).get(ChatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        val listChat: ListView = root.findViewById(R.id.listChat)
        val list = arrayListOf<Chat>()
        list.add(Chat("1", "Android", "http://android.com.ua/images/News/android_logo.png", true, true, "12:07", "android forever"))
        list.add(Chat("2", "iOs", "https://cdn.mos.cms.futurecdn.net/MFHVMYQCeJHMGop4u8VkzJ.jpg", false, false, "12:01", "apple apple apple"))
        list.add(Chat("3", "Xiaomi", "https://i.pinimg.com/originals/68/ab/1e/68ab1e35d6558b90c67f7a3c2c9e90aa.jpg", true, true, "11:00", "top za svoi dengi"))
        list.add(Chat("4", "Huawei", "https://servicefon.lviv.ua/wp-content/uploads/2014/12/huawei-logo-300x300.jpeg", true, false, "14:58", "prosto huawei"))


        val adapterChats = ChatListAdapter(context!!, list)
        listChat.adapter = adapterChats
        listChat.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val chat = list.get(position)
            val action = ChatsFragmentDirections.Action_navigation_chats_to_navigation_messages(chat.imageUrl, "")
            view.findNavController().navigate(action)
        }
        chatsViewModel.getContactId()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}