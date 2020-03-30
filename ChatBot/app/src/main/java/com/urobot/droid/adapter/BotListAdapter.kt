package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.Bot
import com.urobot.droid.db.Messenger


class BotListAdapter(
    private val context: Context,
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

        if (dataSource[position].listMessengers.isEmpty()) {
            holder.addBot.visibility = View.VISIBLE
        }
        for (id in dataSource[position].listMessengers) {
            when (Messenger.Companion.fromValue(id)) {
                Messenger.Telegram -> {
                    holder.iconTelegram.visibility = View.VISIBLE
                    holder.iconTelegram.setOnClickListener(View.OnClickListener { view ->
                        mOnBotClickListener?.onAddBotClick(
                            view, position
                        )
                    })
                }
                Messenger.Viber -> {
                    holder.iconViber.visibility = View.VISIBLE
                    holder.iconTelegram.setOnClickListener(View.OnClickListener { view ->
                        mOnBotClickListener?.onAddBotClick(
                            view, position
                        )
                    })
                }
                Messenger.Facebook -> {
                    holder.iconFacabook.visibility = View.VISIBLE
                    holder.iconTelegram.setOnClickListener(View.OnClickListener { view ->
                        mOnBotClickListener?.onAddBotClick(
                            view, position
                        )
                    })
                }
                Messenger.Vk -> {
                    holder.iconVk.visibility = View.VISIBLE
                    holder.iconTelegram.setOnClickListener(View.OnClickListener { view ->
                        mOnBotClickListener?.onAddBotClick(
                            view, position
                        )
                    })
                }
                Messenger.WhatsApp -> {
                    holder.iconWhatsUp.visibility = View.VISIBLE
                    holder.iconTelegram.setOnClickListener(View.OnClickListener { view ->
                        mOnBotClickListener?.onAddBotClick(
                            view, position
                        )
                    })
                }
                Messenger.Instagram -> {
                    holder.iconInstagramm.visibility = View.VISIBLE
                    holder.iconTelegram.setOnClickListener(View.OnClickListener { view ->
                        mOnBotClickListener?.onAddBotClick(
                            view, position
                        )
                    })
                }
            }
        }

        holder.addBot.setOnClickListener(View.OnClickListener { view ->
            mOnBotClickListener?.onAddBotClick(
                view, position
            )
        })

        holder.change.setOnClickListener(View.OnClickListener { view ->
            mOnBotClickListener?.onChangeClick(
                view, position
            )
        })

        holder.botView.setOnClickListener(View.OnClickListener { view ->
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
        var addBot: TextView
        var iconTelegram: ImageView
        var iconFacabook: ImageView
        var iconViber: ImageView
        var iconVk: ImageView
        var iconInstagramm: ImageView
        var iconWhatsUp: ImageView
        var botView: RelativeLayout

        init {
            title = itemView.findViewById(R.id.titleBot)
            description = itemView.findViewById(R.id.descriptionBot)
            change = itemView.findViewById(R.id.tvChange)
            iconTelegram = itemView.findViewById(R.id.iconTelegram)
            botView = itemView.findViewById(R.id.botView)
            iconFacabook = itemView.findViewById(R.id.iconFacebook)
            iconViber = itemView.findViewById(R.id.iconViber)
            iconVk = itemView.findViewById(R.id.iconVk)
            iconInstagramm = itemView.findViewById(R.id.iconInstagram)
            iconWhatsUp = itemView.findViewById(R.id.iconWhatsApp)
            addBot = itemView.findViewById(R.id.tvAddBot)

        }


    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onAddBotClick(view: View?, position: Int)
        fun onChangeClick(view: View?, position: Int)
    }

    fun addClickListener(itemClickListener: ItemClickListener) {
        mOnBotClickListener = itemClickListener
    }

}