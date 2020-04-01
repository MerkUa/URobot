package com.urobot.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.urobot.android.R
import com.urobot.android.data.model.Chat

class ChatListAdapter(private val context: Context,
                      private val dataSource: ArrayList<Chat>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_chats, parent, false)
        val title: TextView = rowView.findViewById(R.id.titleTextView)
        val description: TextView = rowView.findViewById(R.id.descriptionTextView)
        val time: TextView = rowView.findViewById(R.id.timeTextView)
        val photo: ImageView = rowView.findViewById(R.id.photoView)
        val readStatus: ImageView = rowView.findViewById(R.id.isReadImageView)
        val onlineStatus: ImageView = rowView.findViewById(R.id.isOnlineImageView)


        val chat = getItem(position) as Chat

        title.text = chat.title
        description.text = chat.details
        time.text = chat.lastTime
//        if (chat.isRead)
//            readStatus.visibility = View.VISIBLE

//        if (chat.isOnline)
//            onlineStatus.visibility = View.VISIBLE

        if (chat.imageUrl.isNotEmpty()) {
            Picasso.get().load(chat.imageUrl).into(photo)
        } else {
            Picasso.get().load("https://www.iconsdb.com/icons/preview/black/contacts-xxl.png")
                .into(photo)
        }



        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

}