package com.urobot.droid.ui.fragments.contacts

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.urobot.droid.R
import com.urobot.droid.adapter.ContactListAdapter
import com.urobot.droid.data.model.Contact
import kotlinx.android.synthetic.main.fragment_list_contacts.*


class ContactsMyFragment : Fragment(), ContactListAdapter.ItemClickListener,
    SubscribersViewModel.IContactsContract {

    companion object {
        //Permission code
        private val PERMISSION_CODE = 1007;

    }


    private lateinit var contactsViewModel: SubscribersViewModel
    private val listSubscribers = arrayListOf<Contact>()
    private val adapterChats = ContactListAdapter()
//    private val listContacts: RecyclerView = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsViewModel =
            ViewModelProvider(this).get(SubscribersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list_contacts, container, false)
//        listContacts = root.findViewById(R.id.contacts)
        contactsViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                contactsViewModel.getSubscribers(it.token!!)
            }
        })
        contactsViewModel.setListener(this)

        return root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PermissionChecker.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    loadContacts()
                } else {
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listSubscribers.clear()
//        checkPermissions()
        editTextSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterChats!!.getFilter().filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun onItemClick(view: View?, position: Int) {
        if (position < listSubscribers.size) {
            var contact = listSubscribers.get(position)
            val action =
                ContactsFragmentDirections.actionNavigationContactsToNavigationProfile2(
                    "",
                    contact.name,
                    contact.avatar,
                    contact.phone
                )
//        val action = AddMessengerFragmentDirections.Navigation_to_add_messenger(TELEGTAM)
            findNavController().navigate(action)
            Log.d("onItemClick", "onItemClick " + position)
        }
    }

    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_CONTACTS
                ) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(
                    Manifest.permission.READ_CONTACTS
                )
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                loadContacts()
            }
        }
    }

    fun loadContacts() {
//        val phones = activity?.contentResolver?.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//            null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
//        )
//        while (phones!!.moveToNext()) {
//            val name =
//                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//            val phoneNumber =
//                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//            val id =
//                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID))
//            val photo =
//                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
//
//            list.add(
//                Contact(
//                    id,
//                    name,
//                    phoneNumber,
//                    "",
//                    photo ?: ""
//                )
//            )
//        }
//
//        contacts.layoutManager = LinearLayoutManager(context)
////        listContacts.layoutManager = StickyHeaderLayoutManager()
//        contacts.adapter = adapterChats
//        adapterChats.setData(list)
//        adapterChats.addClickListener(this)
    }

    override fun onGetSubscribersResult(list: ArrayList<Contact>) {
        Log.d("Merk ", "onGetSubscribersResult " + list.size)

        listSubscribers.addAll(list)
        contacts.layoutManager = LinearLayoutManager(context)
//        listContacts.layoutManager = StickyHeaderLayoutManager()
        contacts.adapter = adapterChats
        adapterChats.setData(listSubscribers)
        adapterChats.addClickListener(this)

    }

}