package com.urobot.droid.ui.fragments.message

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.urobot.droid.Helper.Utils
import com.urobot.droid.R
import com.urobot.droid.data.NetModel.Request.Type
import com.urobot.droid.data.model.Author
import com.urobot.droid.data.model.ChatMessage
import kotlinx.android.synthetic.main.message_fragment.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MessageFragment : Fragment(), MessageViewModel.IChatMessageContract {

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var inputField: MessageInput
    private lateinit var adapter: MessagesListAdapter<ChatMessage>
    val br: BroadcastReceiver = MyBroadcastReceiver()
    var listOfMessage: ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.message_fragment, container, false)

        val recipientId = arguments?.let { MessageFragmentArgs.fromBundle(it).idRecipient }

        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        inputField = root.findViewById(R.id.input)
        adapter = MessagesListAdapter<ChatMessage>("-1", imageLoader)
        messageViewModel.setListener(this)
//        adapter.setOnMessageViewClickListener { view, message ->
//            Log.d("ClickListener","ClickListener "+view)


//        }


        val authorMe =
            Author("-1", "Me", "http://android.com.ua/images/News/android_logo.png", false)
        val authorSender = Author("2", "Sender", "", false)

        inputField.setInputListener(InputListener {

            val inputMessage = ChatMessage(1, authorMe, it.toString(), Date())


            messageViewModel.currentUser.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { users ->

                    users?.let {
                        if (recipientId != null) {
                            messageViewModel.sendTextMessage(
                                it.token!!,
                                recipientId,
                                inputField.inputEditText.text.toString()
                            )
                        }
                    }
                })

            //validate and send message
            adapter.addToStart(inputMessage, true)
            true
        })

        inputField.setAttachmentsListener(MessageInput.AttachmentsListener {
            pickImageFromGallery()

        })


//        listChat.click

//        Get Message
        messageViewModel.currentUser.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { users ->
                users?.let {
                    messageViewModel.getMessages(it.token!!, recipientId.toString(), 1)

                }
            })

        val filter = IntentFilter().apply {
            addAction("com.example.broadcast.MY_NOTIFICATION")
        }
        context!!.registerReceiver(br, filter)
        checkPermission()
        return root

    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            if (PermissionChecker.checkSelfPermission(context!!, Manifest.permission.CAMERA) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.CAMERA);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesList.setAdapter(adapter)

        messageViewModel.messageLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                for (item in result.data!!) {
                    Log.d("Merk", "message " + item.senderId)
                    if (item.type == Type.Text.type) {

                        val message = item.data
                        val messageId = item.id

                        val list: ArrayList<ChatMessage> = ArrayList()


                        var author: Author
                        if (item.senderId == "-1") {
                            author = Author(
                                "-1",
                                "Me",
                                "http://android.com.ua/images/News/android_logo.png",
                                false
                            )
                        } else {
                            author = Author("2", "Sender", "", false)
                        }
                        if (message != null) {
                            if (!listOfMessage.contains(messageId)) {
                                listOfMessage.add(messageId!!)
                                list.add(ChatMessage(messageId, author, message))
                                adapter.addToEnd(list, false)
                            }
                        }

                    }

                    if (item.type == Type.Image.type) {
                        val message = item.data

                        var author: Author
                        if (item.senderId == "-1") {
                            author = Author(
                                "-1",
                                "Me",
                                "http://android.com.ua/images/News/android_logo.png",
                                false
                            )
                        } else {
                            author = Author("2", "Sender", "", false)
                        }
                        val listImage: ArrayList<ChatMessage> = ArrayList()

                        val message1 = ChatMessage(item.id, author, "")
                        if (!listOfMessage.contains(item.id)) {
                            listOfMessage.add(item.id!!)
                            message1.setImage(ChatMessage.Image(message!!))
                            listImage.add(message1)
                            adapter.addToEnd(listImage, true)

                        }
                    }

                }

            })
        messageViewModel.pushMessageLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                for (item in result.data!!) {
                    Log.d("Merk", "message " + item.senderId)
                    if (item.type == Type.Text.type) {

                        val message = item.data
                        val messageId = item.id

                        val list: ArrayList<ChatMessage> = ArrayList()


                        var author: Author
                        if (item.senderId == "-1") {
                            author = Author(
                                "-1",
                                "Me",
                                "http://android.com.ua/images/News/android_logo.png",
                                false
                            )
                        } else {
                            author = Author("2", "Sender", "", false)
                        }
                        if (message != null) {
                            if (!listOfMessage.contains(messageId)) {
                                listOfMessage.add(messageId!!)
                                list.add(ChatMessage(messageId, author, message))
                                adapter.addToStart(ChatMessage(messageId, author, message), true)
                            }
                        }

                    }

                    if (item.type == Type.Image.type) {
                        val message = item.data

                        var author: Author
                        if (item.senderId == "-1") {
                            author = Author(
                                "-1",
                                "Me",
                                "http://android.com.ua/images/News/android_logo.png",
                                false
                            )
                        } else {
                            author = Author("2", "Sender", "", false)
                        }
                        val listImage: ArrayList<ChatMessage> = ArrayList()

                        val message1 = ChatMessage(item.id, author, "")
                        if (!listOfMessage.contains(item.id)) {
                            listOfMessage.add(item.id!!)
                            message1.setImage(ChatMessage.Image(message!!))
                            listImage.add(message1)
                            adapter.addToStart(message1, true)

                        }
                    }
                }

            })
        setRecyclerViewScrollListener()

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
                } else {
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MessageFragment.IMAGE_PICK_CODE) {
                val authorMe =
                    Author("1", "Me", "http://android.com.ua/images/News/android_logo.png", false)

                val message1 = ChatMessage(1, authorMe, "")
                message1.setImage(ChatMessage.Image(data?.data.toString()))
                adapter.addToStart(message1, true)

                messageViewModel.currentUser.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { users ->
                        users?.let {
                            val recipientId =
                                arguments?.let { MessageFragmentArgs.fromBundle(it).idRecipient }
                            if (recipientId != null) {
                                val file = File(Utils.getRealPath(context!!, data?.data!!))
                                messageViewModel.sendImageMessage(it.token!!, recipientId, file)
                            }
                        }
                    })
            }
        }
    }

//    private val messagesReceiver: BroadcastReceiver = object : BroadcastReceiver() {
////        override fun onReceive(context: Context, intent: Intent) {
////
////        }
////    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val dataId = intent.getStringExtra("data")
            Log.e("merkR", "onReceive: $dataId")

            val recipientId = arguments?.let { MessageFragmentArgs.fromBundle(it).idRecipient }

            if (dataId == recipientId.toString()) {

                messageViewModel.getNewMessages()

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        context!!.unregisterReceiver(br)
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, MessageFragment.IMAGE_PICK_CODE)
    }

    private fun setRecyclerViewScrollListener() {

        messagesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val ll = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPos = ll.findFirstVisibleItemPosition()
                if (firstVisibleItemPos > totalItemCount / 2) {
                    Log.d(
                        "Merk",
                        "Load " + ll.findFirstVisibleItemPosition() + " - " + totalItemCount
                    )
                    messageViewModel.getOldMessage()

                }
            }
        })
    }

    override fun onMessageSent(messageId: Int) {
        listOfMessage.add(messageId)
    }

}

var imageLoader: ImageLoader = object : ImageLoader {

    override fun loadImage(imageView: ImageView?, url: String?, payload: Any?) {
        Picasso.get().load(url).into(imageView)
    }
}
