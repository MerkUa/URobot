package com.urobot.droid.ui.fragments.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.adapter.ContactListAdapter
import com.urobot.droid.data.model.Contact


class ContactsFragment : Fragment(), ContactListAdapter.ItemClickListener {


    private lateinit var contactsViewModel: ContactsViewModel
    private val list = arrayListOf<Contact>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        contactsViewModel =
            ViewModelProvider(this).get(ContactsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)
//        contactsViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
//        })

        val listContacts: RecyclerView = root.findViewById(R.id.contacts)

        list.add(
            Contact(
                "1",
                "Android",
                "0990003300",
                "",
                "http://android.com.ua/images/News/android_logo.png"
            )
        )
        list.add(
            Contact(
                "2",
                "iOs",
                "0932000000",
                "",
                "https://cdn.mos.cms.futurecdn.net/MFHVMYQCeJHMGop4u8VkzJ.jpg"
            )
        )
//        list.add(
//            Contact(
//                "3",
//                "Xiaomi",
//                "0680000040",
//                "",
//                "https://i.pinimg.com/originals/68/ab/1e/68ab1e35d6558b90c67f7a3c2c9e90aa.jpg"
//            )
//        )
        list.add(
            Contact(
                "4",
                "Huawei",
                "0688894858",
                "",
                "https://servicefon.lviv.ua/wp-content/uploads/2014/12/huawei-logo-300x300.jpeg"
            )
        )
        val adapterChats = ContactListAdapter()
        listContacts.layoutManager = LinearLayoutManager(context)
//        listContacts.layoutManager = StickyHeaderLayoutManager()
        listContacts.adapter = adapterChats
        adapterChats.setData(list)
        adapterChats.addClickListener(this)


//        listContacts.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val chat = list.get(position)
//            val action = ChatsFragmentDirections.Action_navigation_chats_to_navigation_messages(chat.imageUrl, "")
//            view.findNavController().navigate(action)
//        }
        return root
    }

    override fun onItemClick(view: View?, position: Int) {
        var contact = list.get(position)
        val action =
            ContactsFragmentDirections.action_navigation_contacts_to_navigation_profile2(
                contact.name,
                contact.avatar,
                contact.phone
            )
//        val action = AddMessengerFragmentDirections.Navigation_to_add_messenger(TELEGTAM)
        findNavController().navigate(action)
        Log.d("onItemClick", "onItemClick " + position)
    }

}