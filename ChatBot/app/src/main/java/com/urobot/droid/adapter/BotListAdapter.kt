package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.Bot


class BotListAdapter(private val context: Context,
                     private val dataSource: ArrayList<Bot>
) : RecyclerView.Adapter<BotListAdapter.BotItemViewHolder>() {

    private var mOnBotClickListener: ItemClickListener? = null


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_bots, parent, false)
        val viewHolder = BotItemViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: BotItemViewHolder, position: Int) {
        holder.title.text = dataSource[position].title
        holder.description.text = dataSource[position].description
        holder.iconTelegram.visibility = View.VISIBLE
        holder.change.setOnClickListener(View.OnClickListener { view ->
            mOnBotClickListener?.onItemClick(
                view, position
            )
        })
    }

    inner class BotItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var description: TextView
        var change: TextView
        var iconTelegram: ImageView

        init {
            title = itemView.findViewById(R.id.titleBot)
            description = itemView.findViewById(R.id.descriptionBot)
            change = itemView.findViewById(R.id.tvChange)
            iconTelegram = itemView.findViewById(R.id.iconTelegram)
        }


    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun addClickListener(itemClickListener: ItemClickListener) {
        mOnBotClickListener = itemClickListener
    }

}