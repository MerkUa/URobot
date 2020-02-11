package com.urobot.droid.ui.fragments.message

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesList
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


class MessageFragment : Fragment() {

    companion object {

        private  val TAG = "MyBroadcastReceiver"
        fun newInstance() = MessageFragment()
        //image pick code
        private val IMAGE_PICK_CODE = 1000
    }

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var inputField: MessageInput
    private lateinit var adapter: MessagesListAdapter<ChatMessage>
    val br: BroadcastReceiver = MyBroadcastReceiver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.message_fragment, container, false)

        val listChat: MessagesList = root.findViewById(R.id.messagesList)
        val recipientId = arguments?.let { MessageFragmentArgs.fromBundle(it).idRecipient }

        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        inputField = root.findViewById(R.id.input)
        adapter = MessagesListAdapter<ChatMessage>("-1", imageLoader)
//        adapter.setOnMessageViewClickListener { view, message ->
//            Log.d("ClickListener","ClickListener "+view)


//        }


        val authorMe =
            Author("-1", "Me", "http://android.com.ua/images/News/android_logo.png", false)
        val authorSender = Author("2", "Sender", "", false)

//        val message1 = Message("1", authorMe, "Hi")
//        val message2 = Message("2", authorSender, "Hi!")
//        val message3 = Message("1", authorMe, "text")
//        val message4 = Message("2", authorSender, "text")

//        list.add(message1)
//        list.add(message2)
//        list.add(message3)
//        list.add(message3)
//        list.add(message3)
//        list.add(message4)
//
//        adapter.addToEnd(list, false)
//        listChat.setAdapter(adapter)




        inputField.setInputListener(InputListener {

            val inputMessage = ChatMessage(1, authorMe, it.toString(), Date())

            messageViewModel.currentUser.observe(viewLifecycleOwner, androidx.lifecycle.Observer { users ->

                users?.let {
                    if (recipientId != null) {
                        messageViewModel.sendTextMessage(it.token!!, recipientId, inputField.inputEditText.text.toString())
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

        //Get Message
        messageViewModel.currentUser.observe(viewLifecycleOwner, androidx.lifecycle.Observer { users ->

            users?.let {
                messageViewModel.getMessage(it.token!!, recipientId.toString())
            }
        })

//        val filter = IntentFilter().apply {
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        context!!.registerReceiver(br, filter)
//        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        LocalBroadcastManager.getInstance(context!!).registerReceiver(br, IntentFilter())
        context!!.registerReceiver(br, IntentFilter())

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageViewModel.messageLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { result ->

            for (item in result.data!!) {
                Log.d("Merk", "message " + item.senderId)
                if(item.type == Type.Text.type){

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
                    list.add(ChatMessage(messageId, author, message!!))
                    adapter.addToEnd(list, false)
                    messagesList.setAdapter(adapter)
                }

                if(item.type == Type.Image.type){
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

                    val message1 = ChatMessage(1, author, "")
                    listImage.add(message1)
                    message1.setImage(ChatMessage.Image(message!!))
                    adapter.addToEnd(listImage, true)

                }

            }

        })
    }

    //handle result of picked image
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MessageFragment.IMAGE_PICK_CODE) {
                val authorMe = Author("1", "Me", "http://android.com.ua/images/News/android_logo.png", false)

                val message1 = ChatMessage(1, authorMe, "")
                message1.setImage(ChatMessage.Image(data?.data.toString()))
                adapter.addToStart(message1, true)

                messageViewModel.currentUser.observe(viewLifecycleOwner, androidx.lifecycle.Observer { users ->
                    users?.let {
                        val recipientId = arguments?.let { MessageFragmentArgs.fromBundle(it).idRecipient }
                        if (recipientId != null) {
                            val file = File(Utils.getRealPath(context!!, data?.data!!))
                            messageViewModel.sendImageMessage(it.token!!, recipientId, file )
                        }
                    }
                })
            }
        }
    }

//    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent) {
//
//            val id = intent.extras!!.getString("chat_id")
//            val idD= activity?.intent?.getStringExtra("chat_id")
//            val chatId = intent.getStringExtra("chat_id")
//            Log.d("idD", idD)
//            Log.d("receive", id)
//            Log.d("receive", chatId.toString())
//
//
//        }
//    }
//      var myReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        Log.d("ROCK", "TUT BLYA")
//        val action = intent.getStringExtra("action")
//        if(action != null) {
//            Log.d("ROCK", action)
//        }
//
//
//    }
//}


    class MyBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val pendingResult: PendingResult = goAsync()
            val asyncTask = Task(pendingResult, intent)
            asyncTask.execute()
            Log.d(TAG, "tut")


        }

        private class Task(
            private val pendingResult: PendingResult,
            private val intent: Intent
        ) : AsyncTask<String, Int, String>() {

            override fun doInBackground(vararg params: String?): String {
                val id = intent.getStringExtra("data")
                val idD = intent.extras?.getString("data")
                Log.d(TAG, "tut")
                if(id != null  ) {
                    Log.d(TAG, id)

                }
                if(idD!=null){
                    Log.d(TAG, idD)
                }
//                val sb = StringBuilder()
//                Log.d(TAG, "tut")
//                sb.append("Action: ${intent.action}\n")
//                sb.append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
                return toString().also { log ->
                    Log.d(TAG, log)
                }

            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                // Must call finish() so the BroadcastReceiver can be recycled.
                pendingResult.finish()
                Log.d(TAG, "tut")
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

}

var imageLoader: ImageLoader = object : ImageLoader {

    override fun loadImage(imageView: ImageView?, url: String?, payload: Any?) {
        Picasso.get().load(url).into(imageView)
    }
}
