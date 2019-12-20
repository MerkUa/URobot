package com.urobot.droid.ui.fragments.message

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.urobot.droid.R
import com.urobot.droid.data.model.Author
import com.urobot.droid.data.model.Message

import java.util.*
import kotlin.collections.ArrayList


class MessageFragment : Fragment() {


    companion object {
        fun newInstance() = MessageFragment()
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
    }

    private lateinit var viewModel: MessageViewModel
    private lateinit var inputField: MessageInput
    private lateinit var adapter: MessagesListAdapter<Message>


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.message_fragment, container, false)
        val listChat: MessagesList = root.findViewById(R.id.messagesList)

        val chatId = MessageFragmentArgs.fromBundle(arguments).idChat
        val recipientId = MessageFragmentArgs.fromBundle(arguments).idRecipient
        inputField = root.findViewById(R.id.input)
        adapter = MessagesListAdapter<Message>("1", imageLoader)
//        adapter.setOnMessageViewClickListener { view, message ->
//            Log.d("ClickListener","ClickListener "+view)
//        }
        val list: ArrayList<com.urobot.droid.data.model.Message> = ArrayList()


        val authorMe = Author("1", "Me", "http://android.com.ua/images/News/android_logo.png", false)
        val authorSender = Author("2", "Sender", MessageFragmentArgs.fromBundle(arguments).idRecipient, false)

        val message1 = Message("1", authorMe, "Hi")
        val message2 = Message("2", authorSender, "Hi!")
        val message3 = Message("1", authorMe, "text")
        val message4 = Message("2", authorSender, "text")

        list.add(message1)
        list.add(message2)
        list.add(message3)
        list.add(message3)
        list.add(message3)
        list.add(message4)

        adapter.addToEnd(list, false)
        listChat.setAdapter(adapter)




        inputField.setInputListener(InputListener {
            //validate and send message
            val inputMessage = Message("1", authorMe, it.toString(), Date())
            adapter.addToStart(inputMessage, true)
            true
        })

        inputField.setAttachmentsListener(MessageInput.AttachmentsListener {
            pickImageFromGallery()

        })

//        listChat.click

        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        // TODO: Use the ViewModel
    }

    //handle result of picked image
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MessageFragment.IMAGE_PICK_CODE) {
                val authorMe = Author("1", "Me", "http://android.com.ua/images/News/android_logo.png", false)

                val message1 = Message("1", authorMe, "")
                message1.setImage(Message.Image(data?.data.toString()))
                adapter.addToStart(message1, true)

            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, MessageFragment.IMAGE_PICK_CODE)
    }

}

var imageLoader: ImageLoader = object : ImageLoader {

    override fun loadImage(imageView: ImageView?, url: String?, payload: Any?) {
        Picasso.get().load(url).into(imageView)
    }
}
