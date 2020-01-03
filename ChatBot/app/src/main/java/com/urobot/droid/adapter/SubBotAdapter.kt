package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.BotContentItem

class SubBotAdapter(
    private val botList: List<BotContentItem>,
    private val context: Context
) :
    RecyclerView.Adapter<SubBotAdapter.SubBotViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBotViewHolder {
        return SubBotViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_layout_sub_bot_content,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubBotViewHolder, position: Int) {
        val content = botList[position]
    }

    override fun getItemCount(): Int {
        return botList.size
    }

    inner class SubBotViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView
        private val textViewGenre: TextView
        private val imageViewMovie: ImageView

        init {
            textViewTitle = itemView.findViewById(R.id.tv_title)
            textViewGenre = itemView.findViewById(R.id.tv_genre)
            imageViewMovie = itemView.findViewById(R.id.image_view_movie)
        }
    }

}