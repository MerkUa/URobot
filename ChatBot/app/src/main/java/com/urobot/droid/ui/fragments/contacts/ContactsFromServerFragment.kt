package com.urobot.droid.ui.fragments.contacts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.urobot.droid.R
import com.urobot.droid.adapter.ContactListAdapter
import com.urobot.droid.data.model.Contact
import kotlinx.android.synthetic.main.fragment_list_contacts.*


class ContactsFromServerFragment : Fragment(), ContactListAdapter.ItemClickListener,
    ContactsViewModel.IContactsContract {


    private lateinit var contactsViewModel: ContactsViewModel
    private val listContacts = arrayListOf<Contact>()
    val adapterChats = ContactListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listContacts.clear()
        contactsViewModel =
            ViewModelProvider(this).get(ContactsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list_contacts, container, false)
//        contactsViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
//        })

//        val listContacts: RecyclerView = root.findViewById(R.id.contacts)

        contactsViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                contactsViewModel.getContacts(it.token!!)
            }
        })
        contactsViewModel.setListener(this)



        return root
    }

    override fun onItemClick(view: View?, position: Int) {
        if (position < listContacts.size) {
            var contact = listContacts.get(position)
            val action =
                ContactsFragmentDirections.actionNavigationContactsToNavigationProfile2(
                    contact.id,
                    contact.name,
                    contact.avatar,
                    contact.phone
                )
//        val action = AddMessengerFragmentDirections.Navigation_to_add_messenger(TELEGTAM)
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterChats!!.getFilter().filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun onGetContactsResult(list: ArrayList<Contact>) {
        listContacts.clear()
        listContacts.addAll(list)
        contacts.layoutManager = LinearLayoutManager(context)
//        listContacts.layoutManager = StickyHeaderLayoutManager()
        contacts.adapter = adapterChats
        adapterChats.setData(listContacts)
        adapterChats.addClickListener(this)

    }

}