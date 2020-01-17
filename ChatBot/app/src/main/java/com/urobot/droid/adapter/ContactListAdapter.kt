package com.urobot.droid.adapter

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
import com.urobot.droid.R
import com.urobot.droid.data.model.Contact
import org.zakariya.stickyheaders.SectioningAdapter


class ContactListAdapter :
    RecyclerView.Adapter<ContactListAdapter.MyItemViewHolder>(), Filterable {

    var users: ArrayList<Contact>? = null

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    contactListFiltered = dataSource
                } else {
                    val filteredList: MutableList<Contact> = ArrayList<Contact>()
                    for (row in dataSource) { // name match condition. this might differ depending on your requirement
// here we are looking for name or phone number match
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
        dataSource.addAll(items)
        contactListFiltered.addAll(items)
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

        init {
            personNameTextView = itemView.findViewById(R.id.nameTextView)
            personAvatarView = itemView.findViewById(R.id.photoView)
            contactItem = itemView.findViewById(R.id.contactItem)
        }

        override fun onClick(v: View?) {
        }
    }

    inner class MyHeaderViewHolder(itemView: View) :
        SectioningAdapter.HeaderViewHolder(itemView) {
        var titleTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.titleTextView)
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

    }


    fun addClickListener(itemClickListener: ItemClickListener) {
        mOnUserClickListener = itemClickListener
    }

}