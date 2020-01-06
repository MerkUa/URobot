package com.urobot.droid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.urobot.droid.R
import com.urobot.droid.data.model.Contact
import org.zakariya.stickyheaders.SectioningAdapter


class ContactListAdapter :
    RecyclerView.Adapter<ContactListAdapter.MyItemViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    private var mOnUserClickListener: ItemClickListener? = null

    private val dataSource: ArrayList<Contact> = mutableListOf<Contact>() as ArrayList<Contact>

    fun setData(items: ArrayList<Contact>) {
        dataSource.clear()
        dataSource.addAll(items)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return dataSource.size
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
        Log.d("onBindViewHolder", "onCreateViewHolder " + dataSource.size)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_contact, parent, false)
        val viewHolder = MyItemViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder " + dataSource[position].name)
        holder.personNameTextView.text = dataSource[position].name
        Picasso.get().load(dataSource[position].avatar).into(holder.personAvatarView)

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