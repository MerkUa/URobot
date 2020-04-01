package com.urobot.android.adapter

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urobot.android.R
import com.urobot.android.data.model.ContactTag


class TagListAdapter :
    RecyclerView.Adapter<TagListAdapter.TagViewHolder>() {


    private val dataSource: ArrayList<ContactTag> =
        mutableListOf<ContactTag>() as ArrayList<ContactTag>

    fun setData(items: ArrayList<ContactTag>) {
        dataSource.clear()
        dataSource.addAll(items)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return dataSource.size
    }


    inner class TagViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tagTextView: TextView
        var imageView: ImageView

        init {
            tagTextView = itemView.findViewById(R.id.tagName)
            imageView = itemView.findViewById(R.id.tagColor)
        }

        override fun onClick(v: View?) {
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        Log.d("onBindViewHolder", "onCreateViewHolder " + dataSource.size)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_tag, parent, false)
        val viewHolder = TagViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.tagTextView.text = dataSource.get(position).name
        holder.imageView.drawable.colorFilter =
            BlendModeColorFilter(dataSource.get(position).color, BlendMode.SRC_ATOP)
    }

}