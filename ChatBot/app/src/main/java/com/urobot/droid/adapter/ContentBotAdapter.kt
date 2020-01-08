package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.BotData

class ContentBotAdapter(private val botList: List<BotData>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val emptyType = 0
    private val contentType = 1
    private val items: ArrayList<BotData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == emptyType) {
            val emptyView = LayoutInflater.from(parent.context).inflate(R.layout.item_empty_type_create_bot, parent,false)
            AddContentViewHolder(emptyView)
        } else {
            val contentView = LayoutInflater.from(parent.context).inflate(R.layout.item_with_event_create_bot, parent,false)

            ContentViewHolder(contentView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val positionList = botList.size - 1

        return if (position == positionList ) {
            emptyType
        } else {
            contentType
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = botList[position]
//        if(content.level == 1){
//            emptyType
//        }else{
//            contentType
//        }
    }

    override fun getItemCount(): Int {
        return botList.size
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class AddContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        init {

        }


    }
    fun setData(data: ArrayList<BotData>){

    }
}