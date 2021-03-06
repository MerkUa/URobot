package com.urobot.android.ui.fragments.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.urobot.android.R
import com.urobot.android.adapter.TagListAdapter
import com.urobot.android.data.model.ContactTag
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private val list = arrayListOf<ContactTag>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.let { ProfileFragmentArgs.fromBundle(it).id }
        val name = arguments?.let { ProfileFragmentArgs.fromBundle(it).name }
        val avatar = arguments?.let { ProfileFragmentArgs.fromBundle(it).avatar }
        val phone = arguments?.let { ProfileFragmentArgs.fromBundle(it).phone }

        if (id!!.isNotEmpty()) {
            sendMessageButton.visibility = View.VISIBLE
            sendMessageButton.setOnClickListener {

                val action = ProfileFragmentDirections.actionNavigationProfileToNavigationMessages()
                    .setContact(id.toInt())
                view.findNavController().navigate(action)
            }
        }

        textViewName.text = name
        if (avatar?.isNotEmpty()!!) {
            Picasso.get().load(avatar).into(photoView)
        } else {
            Picasso.get().load("https://www.iconsdb.com/icons/preview/black/contacts-xxl.png")
                .into(photoView)
        }
        phoneNumberEditText.setText(phone)

        list.add(ContactTag("", "?????? 1", Color.BLACK))
        list.add(ContactTag("", "?????? 2", Color.CYAN))
        list.add(ContactTag("", "?????? 3", Color.RED))
        list.add(ContactTag("", "?????? 4", Color.GREEN))

        val adapterTags = TagListAdapter()

        listTags.layoutManager = LinearLayoutManager(context)
        listTags.adapter = adapterTags
        adapterTags.setData(list)


    }

}
