package com.urobot.android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.urobot.android.R
import com.urobot.android.data.model.Contact
import com.urobot.android.db.Messenger


class ContactListAdapter :
    RecyclerView.Adapter<ContactListAdapter.MyItemViewHolder>(), Filterable {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    contactListFiltered = dataSource
                } else {
                    val filteredList: MutableList<Contact> = ArrayList<Contact>()
                    for (row in dataSource) { // name match condition. this might differ depending on your requirement
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.phone.contains(
                                charSequence
                            )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    contactListFiltered = filteredList as ArrayList<Contact>
                }
                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                contactListFiltered = filterResults.values as ArrayList<Contact>
                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    private var mOnUserClickListener: ItemClickListener? = null

    private var dataSource: ArrayList<Contact> = mutableListOf<Contact>() as ArrayList<Contact>
    private var contactListFiltered: ArrayList<Contact> =
        mutableListOf<Contact>() as ArrayList<Contact>

    fun setData(items: ArrayList<Contact>) {
        dataSource.clear()
        contactListFiltered.clear()
        notifyDataSetChanged()
        dataSource.addAll(items)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return contactListFiltered.size
    }


    inner class MyItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var personNameTextView: TextView
        var personAvatarView: ImageView
        var contactItem: ConstraintLayout
        var messengerId: ImageView

        init {
            personNameTextView = itemView.findViewById(R.id.nameTextView)
            personAvatarView = itemView.findViewById(R.id.photoView)
            contactItem = itemView.findViewById(R.id.contactItem)
            messengerId = itemView.findViewById(R.id.messengerId)
        }

        override fun onClick(v: View?) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        Log.d("onBindViewHolder", "onCreateViewHolder " + contactListFiltered.size)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_contact, parent, false)
        val viewHolder = MyItemViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder " + contactListFiltered[position].name)
        holder.personNameTextView.text = contactListFiltered[position].name
        if (contactListFiltered[position].avatar.isNotEmpty()) {
            Picasso.get().load(contactListFiltered[position].avatar).into(holder.personAvatarView)
        } else {
            Picasso.get().load("https://www.iconsdb.com/icons/preview/black/contacts-xxl.png")
                .into(holder.personAvatarView)
        }

        holder.contactItem.setOnClickListener(View.OnClickListener { view ->
            mOnUserClickListener?.onItemClick(
                view, position
            )
        })
        when (Messenger.Companion.fromValue(contactListFiltered[position].messengerId)) {
            Messenger.Telegram -> {
                holder.messengerId.setImageResource(R.drawable.telegram)
            }
            Messenger.Viber -> {
                holder.messengerId.setImageResource(R.drawable.viber)
            }
            Messenger.Facebook -> {
                holder.messengerId.setImageResource(R.drawable.facebook)
            }
            Messenger.Vk -> {
                holder.messengerId.setImageResource(R.drawable.vklogo)
            }
            Messenger.WhatsApp -> {
                holder.messengerId.setImageResource(R.drawable.whatsapp)
            }
            Messenger.Instagram -> {
                holder.messengerId.setImageResource(R.drawable.instagram)
            }
        }

    }

    fun addClickListener(itemClickListener: ItemClickListener) {
        mOnUserClickListener = itemClickListener
    }


}